<template>
  <div>
    <button class="btn btn-primary" v-on:click="Controller().openCart()">Cart ({{ numInCart }})</button>

    <div v-if="ModalVo.order" class="modal d-block">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">주문서</h5>
          </div>
          <div class="modal-body">
            <p class="text-danger" v-if="ErrorVo.part.order">{{ErrorVo.message}}</p>
            <table class="table">
              <tbody>
              <tr>
                <th>이름</th>
                <td><input type="text" v-model="OrderVo.buyer"></td>
              </tr>
              <tr>
                <th>우편번호</th>
                <td><input type="text" v-model="OrderVo.address.zip"></td>
              </tr>
              <tr>
                <th>주소(읍면동)</th>
                <td><input type="text" v-model="OrderVo.address.address1"></td>
              </tr>
              <tr>
                <th>주소상세</th>
                <td><input type="text" v-model="OrderVo.address.address2"></td>
              </tr>
              </tbody>
            </table>
            <table class="table">
              <tbody>
              <tr>
                <th>상품명</th>
                <th>개수</th>
                <th>단가</th>
                <th>총액</th>
              </tr>
              <tr v-for="(item, index) in OrderGoodsVo">
                <td>{{ item.productName }}</td>
                <td>{{ item.productCnt }}</td>
                <td>{{ item.productPrice | dollars }}</td>
                <td>{{ item.productPrice * item.productCnt | dollars }}</td>
              </tr>
              </tbody>
            </table>
          </div>
          <div class="modal-footer">
            <button class="btn btn-secondary" v-on:click="Controller().continueShopping()">취소</button>
            <button class="btn btn-primary" v-on:click="Controller().goOrder()">주문</button>
          </div>
        </div>
      </div>
    </div>

    <div v-if="ModalVo.cart" class="modal d-block">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Shopping cart</h5>
          </div>
          <div class="modal-body">
            <p class="text-danger" v-if="ErrorVo.part.cart">{{ErrorVo.message}}</p>
            <table class="table">
              <tbody>
                <tr v-for="(item, index) in cart">
                  <td>{{ item.name }}</td>
                  <td>{{ item.price | dollars }}</td>
                  <td>
                    <button class="btn btn-sm btn-danger" @click="DataBind().removeFromCart(index)">&times;</button>
                  </td>
                </tr>
                <tr>
                  <th></th>
                  <th>{{ total | dollars }}</th>
                  <th></th>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="modal-footer">
            <button class="btn btn-secondary" v-on:click="Controller().continueShopping()">계속 쇼핑</button>
            <button class="btn btn-primary" v-on:click="Controller().goOrderView()">주문</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { dollars } from './filters';

export default {
  name: 'shoppingCart',
  data() {
    return {
      ModalVo: {
        cart: false,
        order: false
      },
      ErrorVo: {
        part: {
          cart: false,
          order: false
        },
        message: ''
      },
      OrderVo: {
        buyer: '',
        address: {
          address1: '',
          address2: '',
          zip: ''
        },
        orderItems: [
          {
            orderQuantity: '',
            productId: ''
          }
        ]
      },
      OrderGoodsVo: [
        {
          productName: '',
          productCnt: 0,
          productPrice: 0
        }
      ]
    }
  },
  computed: {
    inCart() { return this.$store.getters.inCart; },
    numInCart() { return this.inCart.length; },
    cart() {
      return this.$store.getters.inCart.map((cartItem) => {
        return this.$store.getters.forSale.find((forSaleItem) => {
          return cartItem === forSaleItem.invId;
        });
      });
    },
    total() {
      return this.cart.reduce((acc, cur) => acc + cur.price, 0);
    },
  },
  filters: {
    dollars,
  },
  methods: {
    Dao () {
      let self = this
      return {
        orderGoods: {
          excute() {
            let sendObj = self.OrderVo
            self.axios.post('/orders', sendObj, {headers: {'token': self.$store.getters.authorization.token}})
              .then(response => {
                self.Dao().orderGoods.complate(response.data)
              })
              .catch(error => {
                self.Dao().orderGoods.error(error)
              })
          },
          error(error) {
            alert('주문시 에러가 발생 하였습니다.')
            self.DataBind().initOrder()
          },
          complate(responseData) {
            alert('주문 완료 되었습니다.')
            console.info('상품주문완료 ', responseData)
            self.$store.dispatch('clearCart', 1)
            self.DataBind().initOrder()
            self.DataBind().closeOrder()
          }
        },
      }
    },
    Controller () {
      let self = this
      return {
        openCart () {
          self.DataBind().initError()
          self.DataBind().viewCart()
        },
        continueShopping () {
          self.DataBind().closeCart()
          self.DataBind().closeOrder()
        },
        goOrder () {
          self.ErrorVo.part.order = false
          if ( self.OrderVo.buyer === '' ) {
            self.ErrorVo.part.order = true
            self.ErrorVo.message = '주문자 이름이 비어 있습니다.'
          } else if ( self.OrderVo.address.address1 === '' ) {
            self.ErrorVo.part.order = true
            self.ErrorVo.message = '주문자 기본 주소가 비어 있습니다.'
          } else if ( self.OrderVo.address.address2 === '' ) {
            self.ErrorVo.part.order = true
            self.ErrorVo.message = '주문자 상세 주소가 비어 있습니다.'
          } else if ( self.OrderVo.address.zip === '' ) {
            self.ErrorVo.part.order = true
            self.ErrorVo.message = '주문자 우편번호가 비어 있습니다.'
          }
          if ( !self.ErrorVo.part.order ) {
            console.log('주문하러가자!!!')
            console.log('token', self.$store.getters.authorization.token)
            self.Dao().orderGoods.excute()
//            self.Controller().openOrder()
          }
        },
        goOrderView () {
          self.ErrorVo.part.cart = false
          if ( self.inCart.length < 1 ) {
            self.ErrorVo.part.cart = true
            self.ErrorVo.message = '쇼핑 카트가 비어 있습니다.'
          }
          if ( !self.ErrorVo.part.cart ) {
            self.Controller().openOrder()
          }
        },
        openOrder () {
          self.DataBind().closeCart()
          self.DataBind().initOrder()
          self.DataBind().viewOrder()
        }

      }
    },
    DataBind () {
      let self = this
      return {
        viewCart () {
          console.log('view cart', self.ModalVo)
          self.ModalVo.cart = true
        },
        closeCart () {
          self.ModalVo.cart = false
        },
        viewOrder () {
          console.log('view order', self.ModalVo)
          self.ModalVo.order = true
        },
        closeOrder () {
          console.log('view order', self.ModalVo)
          self.ModalVo.order = false
        },
        initOrder () {
          self.OrderVo.buyer = ''
          self.OrderVo.address.zip = ''
          self.OrderVo.address.address1 = ''
          self.OrderVo.address.address2 = ''
          let orderItems = []
          for ( let i in self.inCart ) {
            let tmpId = self.inCart[i]
            let isNew = true
            for ( let j in orderItems ) {
              if ( orderItems[j].productId === tmpId ) {
                isNew = false
              }
            }
            if ( isNew ) {
              let item = {}
              item.productId = tmpId
              item.orderQuantity = 1
              orderItems.push(item)
            } else {
              for ( let j in orderItems ) {
                if ( orderItems[j].productId === tmpId ) {
                  orderItems[j].orderQuantity ++
                }
              }
            }
          }
          self.OrderVo.orderItems = orderItems
          self.OrderGoodsVo = []
          for ( let i in orderItems ) {
            for ( let j in self.$store.getters.forSale ) {
              if ( self.$store.getters.forSale[j].invId ===  orderItems[i].productId ) {
                let orderGood = {}
                orderGood.productName = self.$store.getters.forSale[j].name
                orderGood.productCnt = orderItems[i].orderQuantity
                orderGood.productPrice = self.$store.getters.forSale[j].price
                self.OrderGoodsVo.push(orderGood)
              }
            }
          }
          console.log('items', self.OrderVo)
          console.log('goods', self.OrderGoodsVo)
        },
        closeOrder () {
          self.ModalVo.order = false
        },
        initError () {
          self.ErrorVo.message = ''
          self.ErrorVo.part.cart = false
        },
        removeFromCart(index) { self.$store.dispatch('removeFromCart', index); }
      }
    }
  },
};
</script>
