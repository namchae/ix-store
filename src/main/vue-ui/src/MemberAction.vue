<template>
  <div>
    <button class="btn btn-primary" v-on:click="Controller().openLogin()" v-if="!authInfo.login">Login</button>
    <button class="btn btn-primary" v-on:click="Dao().logout.excute()" v-if="authInfo.login">Logout</button>

    <div v-if="ModalVo.login" class="modal d-block">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Login</h5>
          </div>
          <div class="modal-body">
            <p class="text-danger" v-if="ErrorVo.part.login">{{ErrorVo.message}}</p>
            <form action="/members/web-login" id="login-form" method="get">
            <table class="table">
              <tbody>
                <tr>
                  <th>Email</th>
                  <td><input name="email" type="text" v-model="LoginVo.email" value="gabriel870214@gmail.com"></td>
                </tr>
                <tr>
                  <th>PASSWORD</th>
                  <td><input name="password" type="password" v-model="LoginVo.password" value="namchae12"></td>
                </tr>
              </tbody>
            </table>
            </form>
          </div>
          <div class="modal-footer">
            <button class="btn btn-primary" v-on:click="Controller().submitLogin()">로그인</button>
            <button class="btn btn-primary" v-on:click="Controller().cancelLogin()">취소</button>
            <button class="btn btn-primary" v-on:click="Controller().openJoin()">회원가입</button>
          </div>
        </div>
      </div>
    </div>

    <div v-if="ModalVo.join" class="modal d-block">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">회원가입</h5>
          </div>
          <div class="modal-body">
            <p class="text-danger" v-if="ErrorVo.part.join">{{ErrorVo.message}}</p>
            <table class="table">
              <tbody>
              <tr>
                <th>닉네임</th>
                <td><input type="text" v-model="JoinVo.nickName"></td>
              </tr>
              <tr>
                <th>Email</th>
                <td><input type="text" v-model="JoinVo.email"></td>
              </tr>
              <tr>
                <th>PASSWORD</th>
                <td><input type="password" v-model="JoinVo.password"></td>
              </tr>
              </tbody>
            </table>
          </div>
          <div class="modal-footer">
            <button class="btn btn-primary" v-on:click="Controller().submitJoin()">회원가입</button>
            <button class="btn btn-primary" v-on:click="Controller().cancelJoin()">취소</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'memberAction',
  data() {
    return {
      ModalVo: {
        login: false,
        join: false
      },
      LoginVo: {
        email: '',
        password: ''
      },
      JoinVo: {
        nickName: '1234',
        email: '1234',
        password: 'abcd'
      },
      ErrorVo: {
        part: {
          login: false,
          join: false
        },
        message: ''
      }
    }
  },
  computed: {
    authInfo() {
      return this.$store.getters.authorization
    }
  },
  mounted: function () {
    this.Controller().init()
  },
  methods: {
    Controller () {
      let self = this
      return {
        init () {
          let email = self.$cookies.get("email")
          let token = self.$cookies.get("token")
          if ( email === '' || token === '' || email === null || token === null ) {
            self.DataBind().setAuthorization('', '')
          } else {
            self.DataBind().setAuthorization(email, token)
          }
        },
        openLogin () {
          self.DataBind().initLogin()
          self.DataBind().initError()
          self.DataBind().viewLogin()
        },
        cancelLogin () {
          self.DataBind().initLogin()
          self.DataBind().closeLogin()
        },
        submitLogin () {
          self.ErrorVo.part.login = false
          if ( self.LoginVo.email === '' ) {
            self.ErrorVo.part.login = true
            self.ErrorVo.message = 'email을 입력 하세요'
          } else if ( self.LoginVo.password === '' ) {
            self.ErrorVo.part.login = true
            self.ErrorVo.message = '비밀번호를 입력 하세요'
          }
          if ( !self.ErrorVo.part.login ) {
            self.Dao().login.excute()
          }
        },
        openJoin () {
          self.DataBind().closeLogin()
          self.DataBind().initJoin()
          self.DataBind().initError()
          self.DataBind().viewJoin()
        },
        cancelJoin () {
          self.DataBind().closeJoin()
        },
        submitJoin () {
          self.ErrorVo.part.join = false
          if ( self.JoinVo.nickName === '' ) {
            self.ErrorVo.part.join = true
            self.ErrorVo.message = '별명을 입력 하세요'
          } else if ( self.JoinVo.email === '' ) {
            self.ErrorVo.part.join = true
            self.ErrorVo.message = '이메일을 입력 하세요'
          } else if ( self.JoinVo.password === '' ) {
            self.ErrorVo.part.join = true
            self.ErrorVo.message = '비밀번호를 입력 하세요'
          }
          if ( !self.ErrorVo.part.join ) {
            self.Dao().join.excute()
          }
        }
      }
    },
    Dao () {
      let self = this
      return {
        login: {
          excute() {
            let sendObj = self.LoginVo
            self.axios.post('/members/login', sendObj, null)
              .then(response => {
                self.Dao().login.complate(response, sendObj.email)
              })
              .catch(error => {
                self.Dao().login.error(error)
              })
          },
          error(error) {

          },
          complate(responseData, email) {
            let token = responseData.headers.token
            self.DataBind().setAuthorization(email, token)
            self.DataBind().setAuthorizationCookies(email, token)
            self.DataBind().closeLogin()
          }
        },
        logout: {
          excute() {
            console.log('회원가입')
            /*
                        self.axios.put('/api/reservation/cancel-reason/reason/' + self.$route.params.id, sendObj, {headers: {'AUTH-PART': 'JY10310', 'Accept': 'application/vnd.yanolja.api.reservation-V1+json'}})
                          .then(response => { self.Dao().login.complate(response.data) })
                          .catch(error => { self.Dao().login.error(error) })
            */
            self.Dao().logout.complate('')
          },
          error(error) {

          },
          complate(responseData) {
            self.DataBind().clearAuthorization()
            self.DataBind().setAuthorizationCookies('', '')
          }
        },
        join: {
          excute() {
            console.log('회원가입')
            let sendObj = self.JoinVo
            self.axios.post('/members/signUp', sendObj, null)
              .then(response => {
                self.Dao().join.complate(response)
              })
              .catch(error => {
                self.Dao().join.error(error)
              })
          },
          error(error) {
            let message = error.response.data.message
            console.log('실패', error.response.data)
            if ( message === '' || message === null ) {
              self.ErrorVo.part.join = true
              self.ErrorVo.message = '회원가입에 실패 하였습니다.'
            } else {
              self.ErrorVo.part.join = true
              self.ErrorVo.message = message
            }
            if ( error.response.data.fieldErrors !== null ) {
              self.ErrorVo.part.join = true
              self.ErrorVo.message = error.response.data.fieldErrors[0].field + '는 ' + error.response.data.fieldErrors[0].reason
            }
          },
          complate(responseData) {
            alert('회원가입이 완료 되었습니다.')
            self.DataBind().closeJoin()
          }
        }
      }
    },
    DataBind () {
      let self = this
      return {
        viewLogin () {
          self.ModalVo.login = true
        },
        closeLogin () {
          self.ModalVo.login = false
        },
        initLogin () {
          self.LoginVo.email = ''
          self.LoginVo.password = ''
        },
        viewJoin () {
          self.ModalVo.join = true
        },
        closeJoin () {
          self.ModalVo.join = false
        },
        initJoin () {
          self.JoinVo.nickName = ''
          self.JoinVo.email = ''
          self.JoinVo.password = ''
        },
        initError () {
          self.ErrorVo.message = ''
          self.ErrorVo.part.login = false
          self.ErrorVo.part.join = false
        },
        clearAuthorization () {
          let authInfo = {}
          authInfo.email = ''
          authInfo.token = ''
          self.$store.dispatch('setAuthorization', authInfo)
        },
        setAuthorization (email, token) {
          let authInfo = {}
          authInfo.email = email
          authInfo.token = token
          self.$store.dispatch('setAuthorization', authInfo)
        },
        setAuthorizationCookies (email, token) {
          if ( email !== '' & token !== '' ) {
            self.$cookies.set('email', email)
            self.$cookies.set('token', token)
          } else {
            self.$cookies.remove('email')
            self.$cookies.remove('token')
          }
        }
      }
    }
  }
};
</script>
