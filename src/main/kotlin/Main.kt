import java.net.URL
import java.time.*

fun main(args: Array<String>) {

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
    val attachments: Array<Attachments>? = emptyArray<Attachments>(),
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

open abstract class Attachments(
    open val type: String
)

class AttachmentsPhoto(override val type: String = "photo", val photo: Photo) : Attachments(type)
class AttachmentsVideo(override val type: String = "video", val video: Video) : Attachments(type)
class AttachmentsAudio(override val type: String = "audio", val audio: Audio) : Attachments(type)
class AttachmentsDoc(override val type: String = "doc", val doc: Doc) : Attachments(type)
class AttachmentsNote(override val type: String = "note", val note: Note) : Attachments(type)

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

class Note(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val text: String,
    val date: LocalDate,
    val comments: Int,
    val readComments: Int,
    val viewUrl: URL
) {}


object WallService {
    private var posts = emptyArray<Post>() //массив хранения постов
    private var uniqId: Int = 0 //уникальный айди поста
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

    fun clear() {
        posts = emptyArray()
        uniqId = 0// также здесь нужно сбросить счетчик для id постов, если он у вас используется
    }
}