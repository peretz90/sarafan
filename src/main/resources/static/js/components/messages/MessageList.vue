<template>
  <v-layout align-space-around justify-start column>
    <v-container>
      <message-form :messages="messages" :messageAttr="message" />
    </v-container>
    <v-container>
      <message-row v-for="message in sortedMessages"
                   :message="message"
                   :key="message.id"
                   :editMessage="editMessage"
                   :deleteMessage="deleteMessage"
                   :messages="messages"></message-row>
    </v-container>
  </v-layout>
</template>

<script>
import MessageRow from 'components/messages/MessageRow.vue'
import MessageForm from 'components/messages/MessageForm.vue'
import messagesApi from 'api/messages'
export default {
  name: "MessageList",
  props: ['messages'],
  components: {
    MessageRow,
    MessageForm
  },
  data() {
    return {
      message: null
    }
  },
  computed: {
    sortedMessages() {
      return this.messages.sort((a, b) => -(a.id - b.id))
    }
  },
  methods: {
    editMessage(message) {
      this.message = message
    },
    deleteMessage(message) {
      messagesApi.remove(message.id).then(r => {
        if (r.ok) {
          this.messages.splice(this.messages.indexOf(this.message), 1)
        }
      })
    }
  }
}
</script>

<style scoped>

</style>