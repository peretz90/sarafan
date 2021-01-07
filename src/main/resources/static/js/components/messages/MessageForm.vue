<template>
  <v-layout row>
    <v-text-field
        label="New message"
        placeholder="Write something"
        v-model="text"
    />
    <v-btn @click="save">
      Save
    </v-btn>
  </v-layout>
</template>

<script>
import messagesApi from 'api/messages'

export default {
  name: "MessageForm",
  props: ['messages', 'messageAttr'],
  data() {
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
  methods: {
    save() {
      const message = {
        id: this.id,
        text: this.text
      }

      if (this.id) {
        messagesApi.update(message).then(r =>
            r.json().then(data => {
              const index = this.message.findIndex(item => item === data.id)
              this.messages.splice(index, 1, data)
            })
        )
      } else {
        messagesApi.add(message).then(r =>
            r.json().then(data => {
              const index = this.message.findIndex(item => item === data.id)
              if (index > -1) {
               this.messages.splice(index, 1, data)
              } else {
                this.messages.push(data)
              }
            })
        )
      }
      this.text = ''
      this.id = ''
    }
  }
}
</script>

<style scoped>

</style>