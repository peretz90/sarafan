function getIndex(list, id) {
  for (let i = 0; i < list.length; i++) {
    if (list[i].id === id) {
      return i
    }
  }
  return -1
}

let messageApi = Vue.resource('/message{/id}')

Vue.component('message-form', {
  props: ['messages', 'messageAttr'],
  data: function (){
    return {
      text: '',
      id: ''
    }
  },
  watch: {
    messageAttr: function(newVal, oldVal) {
      this.text = newVal.text
      this.id = newVal.id
    }
  },
  template: `
    <div>
      <input type="text" placeholder="Write something" v-model="text" />
      <input type="button" value="Save" @click="save" />
    </div>
  `,
  methods: {
    save: function () {
      let message = { text: this.text }

      if (this.id) {
        messageApi.update({id: this.id}, message).then(r =>
          r.json().then(data => {
            let index = getIndex(this.messages, data.id)
            this.messages.splice(index, 1, data)
            this.text = ''
            this.id = ''
          })
        )
      } else {
        messageApi.save({}, message).then(r =>
          r.json().then(data => {
            this.messages.push(data)
            this.text = ''
          })
        )
      }
    }
  }
})

Vue.component('message-row', {
  props: ['message', 'editMethod', 'messages'],
  template: `
    <div>
      <i>({{ message.id }})</i> {{ message.text }}
      <span style="position: absolute; right: 0;">
        <input type="button" value="Edit" @click="edit" />
        <input type="button" value="X" @click="del" />
      </span>
    </div>`,
  methods: {
    edit: function () {
      this.editMethod(this.message)
    },
    del: function () {
      messageApi.remove({id: this.message.id}).then(r => {
        if (r.ok) {
          this.messages.splice(this.messages.indexOf(this.message), 1)
        }
      })
    }
  }
})

Vue.component('messages-list', {
  props: ['messages'],
  data: function () {
    return {
      message: null
    }
  },
  template: `
    <div style="position: relative; width: 300px;">
      <message-form :messages="messages" :messageAttr="message" />
      <message-row v-for="message in messages" :message="message" :key="message.id" :editMethod="editMethod" :messages="messages"></message-row>
    </div>`,
  methods: {
    editMethod: function (message) {
      this.message = message
    }
  }
})
                                                                                                                                            
new Vue({
  el: '#app',
  template: `
    <div>
      <div v-if="!profile">Необходимо авторизоваться через <a href="/login">Google</a></div>
      <div v-else>
        <div>{{ profile.name }}&nbsp;<a href="/logout">Выйти</a></div>
        <messages-list :messages="messages" />
      </div>
    </div>`,
  data: {
    messages: frontendData.messages,
    profile: frontendData.profile
  },
  created: function () {
    // messageApi.get().then(result =>
    //   result.json().then(data =>
    //     data.forEach(message => this.messages.push(message))
    //   )
    // )
  },
});