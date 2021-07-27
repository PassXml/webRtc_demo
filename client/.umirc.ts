import {defineConfig} from 'umi';

export default defineConfig({
  nodeModulesTransform: {
    type: 'none',
  },
  routes: [
    {path: '/', component: '@/pages/index'},
  ],
  fastRefresh: {},
  devServer: {
    // https: {
    //   key: './config/key.pem',
    //   cert: './config/cert.pem',
    // },
  },
});
