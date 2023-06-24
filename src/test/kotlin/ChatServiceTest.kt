import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class ChatServiceTest {
    @Before
    fun clearBeforeTest() {
        ChatService.clear()
    }

    @Test
    fun getUnreadChatsCount() {
        //arrange
        //ищем чаты где есть не прочитанные сообщения от 1 пользователя

        val chat1:Chat = ChatService.createChat(idUser = 1, idRecipient = 2, firstMessage = "Привет, я сообщение 2 от 1 в чате 1")
        val mess1Chat1 = ChatService.createMessage(2, 1, 1, "2: Я прочитал сообщение №1 в чате 1")
        val mess2Chat1 = ChatService.createMessage(1, 2, 1, "1: Я прочитал сообщение №1 от 2 в чате 1")
        ChatService.getMessages(1, 0,3) //все прочитаны
        val chat2:Chat = ChatService.createChat(idUser = 1, idRecipient = 2, firstMessage = "Привет, я сообщение 2 от 1 в чате 2")
        val mess1Chat2 = ChatService.createMessage(2, 1, 2, "2: Я прочитал сообщение №1 в чате 2")
        val mess2Chat2 = ChatService.createMessage(1, 2, 2, "1: Я не прочитал сообщение №1 от 2 в чате 2")
        ChatService.getMessages(2, 0,2) // 1 не прочитано (1 от 1 пользв)
        val chat3:Chat = ChatService.createChat(idUser = 2, idRecipient = 1, firstMessage = "Ты тут?, это сообщение 1 от 2 в чате 3")
        val mess1Chat3 = ChatService.createMessage(1, 2, 3, "2: Я не прочитал сообщение №1 в чате 3")
        val mess2Chat3 = ChatService.createMessage(2, 1, 3, "1: Я не прочитал сообщение №1 от 2 в чате 3")
        ChatService.getMessages(3, 1,2) // 2 не прочитано вообще (1 от 1 пользв)
        val chat4:Chat = ChatService.createChat(idUser = 2, idRecipient = 1, firstMessage = "Ты тут?, это сообщение 1 от 2 в чате 4")
        val mess1Chat4 = ChatService.createMessage(1, 2, 4, "2: Я не прочитал сообщение №1 в чате 4")
        val mess2Chat4 = ChatService.createMessage(2, 1, 4, "1: Я не прочитал сообщение №1 от 2 в чате 4")
        //вообще все не прочитаны (1 от 1 пользв)

        //act
        val count: Int = ChatService.getUnreadChatsCount(1)
        //assert
        assertEquals(3, count)
    }

    @Test
    fun getChats() {
        //arrange
        val chat1:Chat = ChatService.createChat(idUser = 1, idRecipient = 2, firstMessage = "Привет, я 1")
        val chat2:Chat = ChatService.createChat(idUser = 1, idRecipient = 2, firstMessage = "Привет, я 1")
        val chat3:Chat = ChatService.createChat(idUser = 2, idRecipient = 1, firstMessage = "Ты тут?")
        //act
        val chatsOwner1 = ChatService.getChats(1)
        //assert
        assertEquals(2, chatsOwner1.size)
        assertEquals(1, chatsOwner1[0].idOwner)
        assertEquals(1, chatsOwner1[1].idOwner)
    }
    @Test
    fun getLastMessages(){
        //assert
        val chat1:Chat = ChatService.createChat(idUser = 1, idRecipient = 2, firstMessage = "Привет, я сообщение 2 от 1 в чате 1")
        val mess1Chat1 = ChatService.createMessage(2, 1, 1, "2: Я прочитал сообщение №1 в чате 1")
        val mess2Chat1 = ChatService.createMessage(1, 2, 1, "1: Я прочитал сообщение №1 от 2 в чате 1")
        ChatService.getMessages(1, 0,3) //все прочитаны
        val chat2:Chat = ChatService.createChat(idUser = 1, idRecipient = 2, firstMessage = "Привет, я сообщение 2 от 1 в чате 2")
        val mess1Chat2 = ChatService.createMessage(2, 1, 2, "2: Я прочитал сообщение №1 в чате 2")
        val mess2Chat2 = ChatService.createMessage(1, 2, 2, "1: Я не прочитал сообщение №1 от 2 в чате 2")
        ChatService.getMessages(2, 0,2) // 1 не прочитано (1 от 1 пользв)
        val chat3:Chat = ChatService.createChat(idUser = 2, idRecipient = 1, firstMessage = "Ты тут?, это сообщение 1 от 2 в чате 3")
        val mess1Chat3 = ChatService.createMessage(1, 2, 3, "2: Я не прочитал сообщение №1 в чате 3")
        val mess2Chat3 = ChatService.createMessage(2, 1, 3, "1: Я не прочитал сообщение №1 от 2 в чате 3")
        ChatService.getMessages(3, 1,2) // 2 не прочитано вообще (1 от 1 пользв)
        val chat4:Chat = ChatService.createChat(idUser = 2, idRecipient = 1, firstMessage = "Ты тут?, это сообщение 1 от 2 в чате 4")
        val mess1Chat4 = ChatService.createMessage(1, 2, 4, "2: Я не прочитал сообщение №1 в чате 4")
        val mess2Chat4 = ChatService.createMessage(2, 1, 4, "1: Я не прочитал сообщение №1 от 2 в чате 4")
        //вообще все не прочитаны (1 от 1 пользв)
        val chat5:Chat = ChatService.createChat(idUser = 1, idRecipient = 2, firstMessage = "Привет, я сообщение 2 от 1 в чате 1")
        ChatService.deleteMessage(5, 1)

        val listExpected = mutableListOf<String>()
        listExpected.add("Чат 1: 1: Я прочитал сообщение №1 от 2 в чате 1")
        listExpected.add("Чат 2: 1: Я не прочитал сообщение №1 от 2 в чате 2")
        listExpected.add("Чат 3: 1: Я не прочитал сообщение №1 от 2 в чате 3")
        listExpected.add("Чат 4: 1: Я не прочитал сообщение №1 от 2 в чате 4")
        listExpected.add("Чат 5: Нет сообщений")
        //act
        val list: MutableList<String> = ChatService.getLastMessages()

        //assert
        assertEquals(listExpected, list)
        assertEquals(5, list.size)
        assertEquals("Чат 4: 1: Я не прочитал сообщение №1 от 2 в чате 4", list[3])
        assertEquals("Чат 5: Нет сообщений", list[4])

    }

//@Test
//    fun getNoMessages(){
//        //assert
//        val chat1:Chat = ChatService.createChat(idUser = 1, idRecipient = 2, firstMessage = "Привет, я сообщение 2 от 1 в чате 1")
//        ChatService.deleteMessage(1, 1)
//        val chat2:Chat = ChatService.createChat(idUser = 1, idRecipient = 2, firstMessage = "Привет, я сообщение 2 от 1 в чате 2")
//        ChatService.deleteMessage(2, 1)
//        //act
//        val str = ChatService.getLastMessages()
//        //assert
//        assertEquals("Нет сообщений ни в одном из чатов", str)
//    }

    @Test
    fun getMessages() {
        //arrange
        val chat:Chat = ChatService.createChat(idUser = 1, idRecipient = 2, firstMessage = "Привет, я 1")
        val mess2 = ChatService.createMessage(2, 1, 1, "Привет, а я 2")
        val mess3 = ChatService.createMessage(1, 2, 1, "Это не прочитанное сообщение от 1")
        val mess4 = ChatService.createMessage(1, 2, 1, "А это не прочитанное сообщение от 2")
        val mess5 = ChatService.createMessage(1, 2, 1, "Это второе не прочитанное сообщение от 1")
        val mess6 = ChatService.createMessage(1, 2, 1, "А это второе не прочитанное сообщение от 2")
        //println(chat.messages)
        chat.messages[0].isRead = true
        chat.messages[1].isRead = true
       // println(chat.messages)
        //act
        val unreadMessages = ChatService.getMessages(1, 2, 3)
//        println(chat.messages)
//        println(unreadMessages)

        //assert
        assertEquals(3, unreadMessages.size)
//        assertEquals("Это не прочитанное сообщение от 1", unreadMessages[0].text)
//        assertTrue(unreadMessages[0].isRead)
//        assertEquals("А это не прочитанное сообщение от 2", unreadMessages[1].text)
//        assertTrue(unreadMessages[1].isRead)
//        assertEquals("Это второе не прочитанное сообщение от 1", unreadMessages[2].text)
//        assertTrue(unreadMessages[2].isRead)

    }

    @Test
    fun createMessage() {
        //arrange
        val chat:Chat = ChatService.createChat(idUser = 1, idRecipient = 2, firstMessage = "Привет, я 1")
        val etalonMessage: Chat.DirectMessages = Chat.DirectMessages(2, 2, 1, "Привет, а я 2", false)
        //act
        val mess = ChatService.createMessage(2, 1, 1, "Привет, а я 2")
        //assert
        assertEquals(etalonMessage, mess)
    }

    @Test
    fun editMessage() {
        //arrange
        val chat:Chat = ChatService.createChat(idUser = 1, idRecipient = 2, firstMessage = "Привет, я 1")
        val mess = ChatService.createMessage(2, 1, 1, "Привет, а я 2")
        //act
        ChatService.editMessage(1, 2, "Здрав буде боярин, последующий за тобой я.")
        //
        assertEquals("Здрав буде боярин, последующий за тобой я.", chat.messages[1].text)

    }

    @Test
    fun deleteMessage() {
        //arrange
        val chat:Chat = ChatService.createChat(idUser = 1, idRecipient = 2, firstMessage = "Привет, я 1")
        val mess = ChatService.createMessage(2, 1, 1, "Привет, а я 2")
        //act
        ChatService.deleteMessage(1, 2)
        //assert
        assertEquals(1, chat.messages.size)
        assertEquals(1, chat.messages[0].id)
    }

    @Test
    fun createChat() {
        //arrange
        val messages: MutableList<Chat.DirectMessages>  = mutableListOf<Chat.DirectMessages>()
        val messageFirst: Chat.DirectMessages = Chat.DirectMessages(1, 1, 2, "Привет", false)
        messages.add(messageFirst)
        val chat1:Chat = Chat(id = 1, lastUniqId = 1, idOwner = 1, messages = messages)
        //act
        val chat2:Chat = ChatService.createChat(idUser = 1, idRecipient = 2, firstMessage = "Привет")
        //assert
        assertEquals(chat1, chat2)
    }

    @Test
    fun deleteChat() {
        //arrange
        val chat1:Chat = ChatService.createChat(idUser = 1, idRecipient = 2, firstMessage = "Привет, я 1")
        val chat2:Chat = ChatService.createChat(idUser = 1, idRecipient = 2, firstMessage = "Привет, я 1")
        val chat3:Chat = ChatService.createChat(idUser = 2, idRecipient = 1, firstMessage = "Ты тут?")
        //act
        ChatService.deleteChat(2)
        //assert
        assertEquals(2, ChatService.chats.size)
        assertEquals(1, ChatService.chats[0].id)
        assertEquals(3, ChatService.chats[1].id)

    }
}