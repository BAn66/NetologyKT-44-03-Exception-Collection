import java.net.URL
import java.security.MessageDigest
import java.time.*

fun main(args: Array<String>) {
//arrange
    val postBeforeAdd1: Post = Post(ownerId = 1)
    val postBeforeAdd2: Post = Post(ownerId = 2)
    val postBeforeAdd3: Post = Post(ownerId = 3)
    val post1: Post = WallService.add(postBeforeAdd1)
    val post2: Post = WallService.add(postBeforeAdd2)
    val post3: Post = WallService.add(postBeforeAdd3)
    val comment1: Comment = Comment(fromId = 1, text = "Первый!!!!111")
    val comment2: Comment = Comment(fromId = 2, text = "Второй")
    val comment3: Comment = Comment(fromId = 3, text = "Третий, автор ты Козел")
    val commentInPost1: Comment = WallService.createComment(2, comment1)
    val commentInPost2: Comment = WallService.createComment(2, comment2)
    val commentInPost3: Comment = WallService.createComment(2, comment3)
    println(commentInPost3.id)
    //act
    var result: Int = WallService.createReportComment(commentInPost3.id, ReportComment.ReasonReport.ABUSE)
    println(result)
    //assert
}

data class Post(
        var id: Int = 0,
        val ownerId: Int = 0,
        val fromId: Int = 0,
        val createdBy: Int = 0,
        val date: LocalDate = LocalDate.now(),
        var text: String = "empty",
        val replyOwnerId: Int = 0,
        val replyPostId: Int = 0,
        val friendsOnly: Boolean = false,
        var comments: Comments = Comments(),
        val copyright: String = "$ownerId",
        val likes: Likes? = Likes(),
        val reposts: Reposts? = Reposts(),
        val views: Int = 0,
        val postType: PostType = PostType.POST,
        val postSource: PostSource? = PostSource(),
        val attachments: Array<Attachments> = emptyArray<Attachments>(),
        val geo: Geo? = Geo(),
        val signerId: Int = 0,
        //val copyHistory: Array<Post> = emptyArray<Post>(),
        val canPin: Boolean = true,
        val canDelete: Boolean = true,
        val canEdit: Boolean = true,
        val isPinned: Boolean = false,
        val markedAsADS: Boolean = false,
        val isFavorite: Boolean = false,
        val postponedId: Int = 0
) {
    enum class PostType {
        POST, COPY, REPLY, POSTPONE, SUGGEST
    }

    class Comments(
            count: Int = 0,
            canPost: Boolean = true,
            groupsCanPostType: Boolean = true,
            canClose: Boolean = true,
            canOpen: Boolean = true
    )

    class Likes(
            val count: Int = 0,
            val userLikes: Boolean = true,
            val canLike: Boolean = true,
            val canPublish: Boolean = true
    )

    class Reposts(
            val count: Int = 0,
            val userReposted: Boolean = false
    )

    class PostSource {}

    class Geo {}
}

interface Identifiable {
    var id: Int
}

open abstract class Attachments(
        open val type: String
)

data class AttachmentsPhoto(override val type: String = "photo", val photo: Photo) : Attachments(type)
data class AttachmentsVideo(override val type: String = "video", val video: Video) : Attachments(type)
data class AttachmentsAudio(override val type: String = "audio", val audio: Audio) : Attachments(type)
data class AttachmentsDoc(override val type: String = "doc", val doc: Doc) : Attachments(type)
data class AttachmentsNote(override val type: String = "note", val note: Note) : Attachments(type)

class Photo(
        val id: Int,
        val albumId: Int,
        val ownerId: Int,
        val userId: Int,
        val text: String,
        val date: LocalDate,
        val sizes: Array<Sizes> = emptyArray<Sizes>(),
        val widthOriginal: Int,
        val heightOriginal: Int
) {
    class Sizes(
            val type: String,
            val url: URL,
            val width: Int,
            val height: Int
    ) {}
}

class Video(
        val id: Int,
        val ownerId: Int,
        val title: String,
        val description: String,
        val duration: Int,
        val photo320: URL?,
        val firstFrame320: URL?,
        val date: LocalDate,
        val addingDate: LocalDate,
        val views: Int,
        val comments: Int,
        val player: URL,
        val platform: String,
        val canEdit: Boolean = true,
        val canAdd: Boolean = true,
        val isPrivate: Boolean = false,
        val accessKey: String,
        val processing: Boolean = false,
        val live: Boolean = false,
        val upcoming: Boolean = false,
        val isFavorite: Boolean = false

) {}

class Audio(
        val id: Int,
        val ownerId: Int,
        val artist: String,
        val title: String,
        val duration: Int,
        val url: URL,
        val lyricsId: Int,
        val albumId: Int,
        val genreId: Int,
        val date: LocalDate,
        val noSearch: Boolean = false,
        val isHQ: Boolean = true
) {}

class Doc(
        val id: Int,
        val ownerId: Int,
        val title: String,
        val size: Int,
        val url: URL,
        val date: LocalDate,
        val type: Int = 1
) {}

data class Note(
        override var id: Int = 0,
        val ownerId: Int = 0,
        val title: String = "empty tittle",
        var text: String = "empty text",
        val date: LocalDate = LocalDate.now(),
        val comments: Int = 0,
        val readComments: Int = 0,
        //val viewUrl: URL = URL("localhost"),
        val privacyView: Boolean = true,
        val canComment: Boolean = true,
        val textWiki: String = "_"
) : Identifiable {}

data class Comment(
        override var id: Int = 0,
        var fromId: Int = 0,
        val date: LocalDate = LocalDate.now(),
        var text: String = "empty comment",
//    val donut: Donut = Donut(),
        val replyToUser: Int = 0,
        val replyToComment: Int = 0,
        val attachments: Array<Attachments> = emptyArray<Attachments>(),
        val parentsStack: Array<Int> = emptyArray<Int>(),
        val threadComments: ThreadComments = ThreadComments(),
        var isDeleted: Boolean = false
) : Identifiable {
    class ThreadComments(
            val count: Int = 0,
            val items: Array<Comment> = emptyArray<Comment>(),
            val canPost: Boolean = true,
            val showReplyButton: Boolean = true,
            val groupsCanPost: Boolean = true
    )
}

data class ReportComment(
        var ownerId: Int = 0,
        val commentId: Int = 0,
        var reason: ReasonReport?
) {
    enum class ReasonReport(value: Int) {
        SPAM(0),
        PORN(1),
        EXTIMIZM(2),
        DRUGS(4),
        ADULT(5),
        ABUSE(6),
        SUICAID(8)
    }
}

data class Chat(
        override var id: Int = 0,
        var lastUniqId: Int = 0,
        val idOwner: Int = 0,
        var messages: MutableList<DirectMessages> = mutableListOf<DirectMessages>()
) : Identifiable {
    data class DirectMessages(
            override var id: Int = 0,
            val idSender: Int = 0,
            val idRecipient: Int = 0,
            var text: String = "",
            var isRead: Boolean = false
    ) : Identifiable
}

object ChatService {
    var chats = mutableListOf<Chat>()
    var uniqIdChat = 0

    //Видеть, сколько чатов не прочитано (например, service.getUnreadChatsCount).
    // В каждом из таких чатов есть хотя бы одно непрочитанное сообщение.
    fun getUnreadChatsCount(idUser: Int): Int {

        return chats.filter { it ->
            (it.messages.filter { !it.isRead }).isNotEmpty()
        }.filter { it ->
            (it.messages.filter { it.idSender==idUser }).isNotEmpty()
        }.size



    }

    //Получить список чатов
    fun getChats(idOwner: Int): MutableList<Chat> {
        return chats.filter { it.idOwner == idOwner }.toMutableList()
    }

    //Типа моя функция расширения, помечает диапазон сообщений как прочитанный
    private fun MutableList<Chat.DirectMessages>.markRead(idStart: Int, idEnd: Int) {
        this.filter { it.id in (idStart + 1)..idEnd }.forEach { it.isRead = true }
    }

    //Получить список сообщений из чата
    fun getMessages(idChat: Int, idLastReadMessages: Int, quantityMessage: Int): MutableList<Chat.DirectMessages> {
        val messages = chats.filter { it.id == idChat }[0].messages.filter { it.id > idLastReadMessages }.toMutableList()
        val messagesToRead = messages.filter { messages.indexOf(it) <= quantityMessage - 1 }.toMutableList()
        chats.filter { it.id == idChat }[0].messages.markRead(idLastReadMessages, messagesToRead.last().id)
        return messagesToRead
    }

    fun createMessage(idUser: Int, idTo: Int, idChat: Int, text: String): Chat.DirectMessages {
        chats[getIndexById(idChat, chats)].lastUniqId++
        val mess = Chat.DirectMessages(chats[getIndexById(idChat, chats)].lastUniqId, idUser, idTo, text)
        chats[getIndexById(idChat, chats)].messages.add(mess)
        return chats[getIndexById(idChat, chats)].messages.last()
    }

    fun editMessage(idChat: Int, idMessage: Int, text: String) {
        val messagesForEdit = chats[getIndexById(idChat, chats)].messages
        messagesForEdit[getIndexById(idMessage, messagesForEdit)].text = text
        chats[getIndexById(idChat, chats)].messages = messagesForEdit
    }

    fun deleteMessage(idChat: Int, idMessage: Int) {
        val messagesForEdit = chats[getIndexById(idChat, chats)].messages
        messagesForEdit.remove(messagesForEdit[getIndexById(idMessage, messagesForEdit)])
        chats[getIndexById(idChat, chats)].messages = messagesForEdit
    }

    fun createChat(idUser: Int, idRecipient: Int, firstMessage: String): Chat {
        var messages = mutableListOf<Chat.DirectMessages>()
        uniqIdChat++
        chats.add(Chat(uniqIdChat, idOwner = idUser, messages = messages))
        createMessage(idUser, idRecipient, uniqIdChat, firstMessage)
        return chats.last()
    }

    fun deleteChat(idChat: Int): Boolean {
        return chats.remove(chats[getIndexById(idChat, chats)])
    }

    fun clear() {
        chats.removeAll(chats)
        uniqIdChat = 0
        // также здесь нужно сбросить счетчик для id постов, если он у вас используется
    }
}

object WallService {
    private var posts = emptyArray<Post>() //массив хранения постов
    private var uniqId: Int = 0 //уникальный айди поста
    private var comments = emptyArray<Comment>()
    private var reportComments = emptyArray<ReportComment>()
    private var uniqIdComment = 0
    fun add(post: Post): Post { // добавляем пост в массив с присвоением уникального айди и начальной записи
        uniqId++
        post.id = uniqId
        post.text = "Запись #$uniqId"
        posts += post
        return posts.last()
    }

    fun update(post: Post): Boolean { //Обновляем пост в массиве
        var update: Boolean = false
        for (oldPost in posts) {
            if (oldPost.id == post.id) {
                posts[oldPost.id - 1] = post.copy()
                update = true
            }
        }
        return update
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        var isFoundPost = false
        for (post in posts) {
            if (post.id == postId) {
                uniqIdComment++
                comment.id = uniqIdComment
                isFoundPost = true
                comments += comment.copy()// лучше копировать, чтоб не могли изменить извне сам комментарий который передается в параметры
            }
        }
        if (!isFoundPost) throw PostNotFoundException("Post not found")
        //return comments.last()
        return comment
    }

    fun createReportComment(commentId: Int, reasonReport: ReportComment.ReasonReport?): Int {
        val rReport = reasonReport ?: throw SomethingWrongException("Не указана причина репорта")
        var isFoundComment = false
        for (comment in comments) {
            if (comment.id == commentId) {
                isFoundComment = true
                reportComments += ReportComment(comment.fromId, commentId, rReport)
            }
        }
        if (!isFoundComment) throw SomethingWrongException("Comment not found")
        return 1
    }


    fun clear() {
        posts = emptyArray()
        uniqId = 0// также здесь нужно сбросить счетчик для id постов, если он у вас используется
        reportComments = emptyArray()
        uniqIdComment = 0
    }
}

object NoteService {
    private var notes = mutableListOf<Note>()
    private var uniqId: Int = 0
    private var noteComments = mutableListOf<Comment>()
    private var uniqIdComment = 0

    fun add(note: Note): Note {//Создает новую заметку у текущего пользователя.
        uniqId++
        //note.id = uniqId // Это нельзя потому что заменяет данные входного объекта
        //note.text = "Записочка #$uniqId" // А нам надо в нотес добавить свой объект с своими изменениями

        notes.add(note.copy(id = uniqId, text = "Записочка #$uniqId"))
        return notes.last()
    }

    fun createComment(comment: Comment, fromIdNote: Int): Comment {//Добавляет новый комментарий к заметке.
        uniqIdComment++
        val createComment: Comment = comment.copy()
        createComment.id = uniqIdComment
        createComment.fromId = fromIdNote
        createComment.text = "Комментарий #$uniqId"
        noteComments.add(createComment)
        return noteComments.last()
    }

    fun delete(idNote: Int): Boolean {//Удаляет заметку текущего пользователя и НАВСЕГДА удаляет из массива комментарии к этой записи)
        val indexNote: Int = getIndexById(idNote, notes)

        val newCommentsList: MutableList<Comment> = mutableListOf<Comment>()

        for (comment in noteComments) {
            if (comment.fromId != idNote) {
                newCommentsList.add(comment)
            }
        }
        noteComments = newCommentsList
        return notes.remove(notes[indexNote])
    }

    fun deleteComment(idComment: Int) {//Удаляет комментарий к заметке, а именно помечает комментарий как удаленный
        noteComments[getIndexById(idComment, noteComments)].isDeleted = true
    }

    fun edit(idNote: Int, newText: String) {//Редактирует заметку текущего пользователя, заменяет текст
        val indexNote: Int = getIndexById(idNote, notes)
        val editedComment = getById(idNote)
        editedComment.text = newText
        notes[indexNote] = editedComment
    }

    fun editComment(idComment: Int, newText: String) {//Редактирует указанный комментарий у заметки.
        val indexEditedComment = getIndexById(idComment, noteComments)
        val editedComment = noteComments[indexEditedComment].copy()
        if (!editedComment.isDeleted) {
            editedComment.text = newText
            noteComments[indexEditedComment] = editedComment
        } else throw SomethingWrongException("Данный комментарий удален")
    }

    fun get(): MutableList<Note> {//Возвращает список заметок, созданных пользователем.
        val notesReturn = mutableListOf<Note>()
        notesReturn.addAll(notes)
        return notesReturn
    }

    fun getById(idNote: Int): Note {//Возвращает заметку по её id.
        return (notes.getOrNull(getIndexById(idNote, notes))
                ?: throw SomethingWrongException("Такой записи нет")).copy()
    }

    fun getComments(): MutableList<Comment> {//Возвращает список комментариев к заметке.(не удаленных)
        val notDeletedComments: MutableList<Comment> = mutableListOf<Comment>()
        for (comment in noteComments) {
            if (!comment.isDeleted) {
                notDeletedComments.add(comment)
            }
        }
        return notDeletedComments
    }

    fun restoreComment(idComment: Int) {// Восстанавливает удаленный комментарий
        val indexComment = getIndexById(idComment, noteComments)
        val restoreComment =
                (noteComments.getOrNull(indexComment) ?: throw SomethingWrongException("Такого комментария нет")).copy()
        if (restoreComment.isDeleted) {
            restoreComment.isDeleted = false
            noteComments[indexComment] = restoreComment
        } else {
            throw SomethingWrongException("Данный комментарий не удален")
        }
    }

    fun clear() {
        notes.removeAll(notes)
        uniqId = 0
        noteComments.removeAll(noteComments)
        uniqIdComment = 0
        // также здесь нужно сбросить счетчик для id постов, если он у вас используется
    }
//Старая версия метода с дженериками
//    private fun <T> getIndexById(id: Int, list: MutableList<T>): Int { //возврат индекса записи в коллекции
//        var index: Int = 0
//        var isExist: Boolean = false
//
//        for (element in list) {
//            when (element) {
//                is Note -> if (element.id == id) {
//                    index = list.indexOf(element)
//                    isExist = true
//                }
//
//                is Comment -> if (element.id == id) {
//                    index = list.indexOf(element)
//                    isExist = true
//                }
//
//                else -> throw SomethingWrongException("Задан неверный список записей или комментариев")
//            }
//        }
//        if (!isExist) throw SomethingWrongException("Такой записи нет!!!")
//        return index
//    }

    //для работы этого метода добавлен интерфейс Identifiable, чтобы не прописывать when

}

fun <T : Identifiable> getIndexById(id: Int, list: MutableList<T>): Int { //возврат индекса записи в коллекции
    var index: Int = 0
    var isExist: Boolean = false
    for (element in list) {
        if (element.id == id) {
            index = list.indexOf(element)
            isExist = true
        }
    }
    if (!isExist) throw SomethingWrongException("Такой записи нет!!!")
    return index
}

class PostNotFoundException(message: String) : SomethingWrongException(message)
open class SomethingWrongException(message: String) : Exception(message)