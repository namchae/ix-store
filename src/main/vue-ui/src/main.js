import Vue from 'vue';
import App from './App.vue';
import store from './store';
import axios from 'axios'
import VueCookies from 'vue-cookies'

Vue.prototype.axios = axios
Vue.use(VueCookies)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  render: h => h(App),
});
