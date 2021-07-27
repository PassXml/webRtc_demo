import styles from './index.less';
import sock, {createRTCPeerConnection, getMedia, login} from "@/util/socket";
import {useCallback, useEffect, useRef, useState} from "react";

let socketId = ''
let userConnection: any = {}
let createConnect: Function = async () => {
  console.log("原始")
}
sock.onmessage = (e: any) => {
  if (e.data === "") {
    return
  }
  const obj = JSON.parse(e.data)
  switch (obj[0]) {
    case "login":
      socketId = obj[1]
      sock.sendMsg(["userJoin", "1"])
      break
    case 'ping':
      sock.sendMsg(["pong"])
      break
    case 'userJoin':
      createConnect(obj[1], obj[2], obj[3])
      break
    case 'userLeft':
      delete userConnection[obj[1]]
      break
    case "signal":
      const fromId = obj[1];
      const signal = JSON.parse(obj[2])
      if (fromId != socketId) {
        if (signal.sdp) {
          userConnection[fromId].setRemoteDescription(new RTCSessionDescription(signal.sdp)).then(() => {
            if (signal.sdp.type == 'offer') {
              userConnection[fromId].createAnswer().then((description: RTCSessionDescription) => {
                userConnection[fromId].setLocalDescription(description).then(() => {
                  sock.sendSignal(fromId, {'sdp': userConnection[fromId].localDescription})
                })
              })
            }
          })
        }
        if (signal.ice) {
          userConnection[fromId].addIceCandidate(new RTCIceCandidate(new RTCIceCandidate(signal.ice))).catch(reason => console.error(reason))
        }
      }
      break
  }
};


const VideoConferencing = (arrayStream: MediaStream[]) => {
  console.log("arrayStream", arrayStream);
  return (
    <div>
      {arrayStream.map(function (stream, i) {
        return (
          <div>
            <video
              ref={video => video.srcObject = stream}
              key={"video" + i}
              width={500}
              height={500}
              autoPlay
              playsInline
              controls
            />
          </div>
        );
      })}
    </div>
  );
};
export default function IndexPage() {
  const localMediaRef = useRef(null)
  const [userMediaInfo, setUserMediaInfo] = useState([])

  useEffect(() => {
    getMedia({
      audio: false,
      video: true,
    }).then(localMedia => {
      localMediaRef.current.srcObject = localMedia
      createConnect = async (id: string, count: number, clients: string) => {
        for (let i = 0; i < clients.length; i++) {
          const socketListId = clients[i]
          if (!userConnection[socketListId]) {
            userConnection[socketListId] = await createRTCPeerConnection();
            userConnection[socketListId].onicecandidate = (ev: any) => {
              if (ev.candidate != null) {
                sock.sendSignal(socketListId, {'ice': ev.candidate});
              }
            }
            userConnection[socketListId].onaddstream = ev => {
              if (ev.stream) {
                setUserMediaInfo([...userMediaInfo, ev.stream])
              }
            }
            //Add the local video stream
            userConnection[socketListId].addStream(localMedia);
          }
        }
        if (count >= 2) {
          userConnection[id].createOffer().then((description: RTCSessionDescriptionInit) => {
            userConnection[id].setLocalDescription(description).then(() => {
              sock.sendSignal(id, {'sdp': userConnection[id].localDescription})
            })
          })
        }
      }
      login()
    });
  })
  return (
    <div>
      <div>
        <video ref={localMediaRef} width={500} height={500} autoPlay/>
      </div>
      <div className="video-list">
        {VideoConferencing(userMediaInfo)}
      </div>
      <h1 className={styles.title}>Page index</h1>
    </div>
  );
}
