<template>
  <v-container>
    <v-layout align-space-around justify-start column>
      <v-container>
        <message-form :messageAttr="message" />
      </v-container>
      <v-container>
        <message-row v-for="message in sortedMessages"
                     :message="message"
                     :key="message.id"
                     :editMessage="editMessage"
                     :messages="messages"></message-row>
      </v-container>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from 'vuex'
import MessageRow from 'components/messages/MessageRow.vue'
import MessageForm from 'components/messages/MessageForm.vue'
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
  computed: mapGetters(['sortedMessages']),
  methods: {
    editMessage(message) {
      this.message = message
    },
  }
}
</script>

<style scoped>

</style>