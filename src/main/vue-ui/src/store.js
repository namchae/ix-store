import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    forSale: [],
    inCart: [],
    authorization: {
      email: '',
      token: '',
      login: false
    }
  },
  getters: {
    forSale: state => state.forSale,
    inCart: state => state.inCart,
    authorization: state => state.authorization,
  },
  mutations: {
    CLEAR_CART(state, invId) { state.inCart = []; },
    ADD_TO_CART(state, invId) { state.inCart.push(invId); },
    REMOVE_FROM_CART(state, index) { state.inCart.splice(index, 1); },
    SET_FOR_SALE(state, goods) {
      state.forSale = goods
    },
    SET_AUTHORIZATION(state, authInfo) {
      console.log('인증 정보 저장', authInfo)
      if ( authInfo.email !== '' & authInfo.token !== '' ) {
        state.authorization.email = authInfo.email
        state.authorization.token = authInfo.token
        state.authorization.login = true
      } else {
        state.authorization.email = ''
        state.authorization.token = ''
        state.authorization.login = false
      }
    },
  },
  actions: {
    addToCart(context, invId) { context.commit('ADD_TO_CART', invId); },
    removeFromCart(context, index) { context.commit('REMOVE_FROM_CART', index); },
    setAuthorization(context, authInfo) { context.commit('SET_AUTHORIZATION', authInfo); },
    setGoodsList(context, goods) { context.commit('SET_FOR_SALE', goods); },
    clearCart(context, clear) { context.commit('CLEAR_CART', clear); },
  },
});
