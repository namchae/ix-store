<template>
  <div id="app" class="container my-5">
    <div class="row mb-3">
      <div class="col-md-9">
        <h1>KAKAO Friends Test Store</h1>
      </div>
      <div class="col-md-1">
        <ShoppingCart />
      </div>
      <div class="col-md-2">
        <MemberAction />
      </div>
    </div>

    <div class="row">
      <Item
        v-for="item in forSale"
        :key="item.invId"
        :invId="item.invId"
        :name="item.name"
        :image="item.image"
        :price="item.price" />
    </div>
  </div>
</template>

<script>
import Item from './Item.vue';
import ShoppingCart from './ShoppingCart.vue';
import MemberAction from './MemberAction.vue';

export default {
  name: 'app',
  computed: {
    forSale() { return this.$store.getters.forSale; },
  },
  mounted: function () {
    this.Dao().loadGoods.excute()
  },
  components: {
    Item,
    ShoppingCart,
    MemberAction
  },
  methods : {
    DataBind () {
      let self = this
      return {
        setGoods (goods) {
          self.$store.dispatch('setGoodsList', goods)
        }
      }
    },
    Dao () {
      let self = this
      return {
        loadGoods: {
          excute() {
            self.axios.get('/products', null)
              .then(response => {
                self.Dao().loadGoods.complate(response.data)
              })
              .catch(error => {
                self.Dao().loadGoods.error(error)
              })
          },
          error(error) {

          },
          complate(responseData) {
            console.info('상품목록 ', responseData)
            let goods = []
            for (let i in responseData) {
              let good = {}
              good.invId = responseData[i].productId
              good.name = responseData[i].productName
              good.image = responseData[i].productImage
              good.price = responseData[i].productPrice
              goods.push(good)
            }
            self.DataBind().setGoods(goods)
          }
        },
      }
    },
  }
};
</script>
