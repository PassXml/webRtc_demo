const Socket = require("sockjs-client");

const sock = new Socket("http://127.0.0.1:8080/ws");


sock.onclose = function () {
  console.log('close');
};

function delay(ms: number) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

sock.sendMsg = (e: any) => {
  (async () => {
    const data = JSON.stringify(e);
    while (true) {
      if (sock.readyState) {
        break
      }
      await delay(2000)
    }
    sock.send(data)
  })()
}
sock.onopen = function () {
};

sock.sendSignal = (id: string, e: any) => {
  sock.sendMsg(["signal", id, e])
}
//WEBRTC

window.onbeforeunload = ev => {
  sock.close()
}


export const getMedia = async (config: MediaStreamConstraints) => {
  return navigator.mediaDevices.getUserMedia(config)
}
export const createRTCPeerConnection = async (config?: RTCConfiguration) => {
  return new RTCPeerConnection(config)
}

export const login = () => {
  sock.sendMsg(["login", {"name": new Date().getTime() + ""}])
}

export default sock
