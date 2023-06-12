import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import java.time.LocalDate

class WallServiceTest {
    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }
    @Test
    fun addPost() {
        //arrange
        val postBeforeAdd1:Post = Post(ownerId = 1)
        val postBeforeAdd2:Post = Post(ownerId = 2)
        val postBeforeAdd3:Post = Post(ownerId = 3)
        //act
        val post1:Post = WallService.add(postBeforeAdd1)
        val post2:Post = WallService.add(postBeforeAdd2)
        val post3:Post = WallService.add(postBeforeAdd3)


        //assert
        assertEquals(1, post1.id)
        assertEquals(2, post2.id)
        assertEquals(3, post3.id)
    }
    @Test
    fun updateExistingTrue() {
        //arrange
        val postBeforeAdd:Post = Post(ownerId = 1) //исходный пост
        val postBeforeUpdate:Post = WallService.add(postBeforeAdd) //Пост хранящийся в массиве, создан из исходного поста
        //act
        postBeforeAdd.text = "Update Record" //измененный исходный пост
        val isUpdate:Boolean = WallService.update(postBeforeAdd)//меняем данные поста в массиве
        //assert
        println(isUpdate)
        assertTrue(isUpdate) //пост нашелся, текст изменен
    }

    @Test
    fun updateExistingFalse() {
        //arrange
        val postBeforeAdd1:Post = Post(ownerId = 1)
        val postBeforeAdd2:Post = Post(ownerId = 2)
        val postBeforeAdd3:Post = Post(ownerId = 3)
        //act
        val post1:Post = WallService.add(postBeforeAdd1)
        val post2:Post = WallService.add(postBeforeAdd2)
        val post3:Post = WallService.add(postBeforeAdd3)

        val postUpdate:Post = Post(id = 4, ownerId = 4, text="Update Record")
        //act
        //println(WallService.update(postUpdate))
        val isUpdate = WallService.update(postUpdate)
        //assert
        assertFalse(isUpdate)
    }

    @Test
    fun createCommentToPost(){
        //arrange
        val postBeforeAdd1:Post = Post(ownerId = 1)
        val postBeforeAdd2:Post = Post(ownerId = 2)
        val postBeforeAdd3:Post = Post(ownerId = 3)
        val post1:Post = WallService.add(postBeforeAdd1)
        val post2:Post = WallService.add(postBeforeAdd2)
        val post3:Post = WallService.add(postBeforeAdd3)
        val comment:Comment = Comment(fromId = 1, text = "Первый!!!!111")
        //act
        val commentInPost:Comment = WallService.createComment(2, comment)
        //
        assertEquals(comment.text, commentInPost.text)
    }

    @Test(expected = PostNotFoundException::class)
    fun createCommentToPostButPostNotFound(){
        //arrange
        val postBeforeAdd1:Post = Post(ownerId = 1)
        val postBeforeAdd2:Post = Post(ownerId = 2)
        val postBeforeAdd3:Post = Post(ownerId = 3)
        val post1:Post = WallService.add(postBeforeAdd1)
        val post2:Post = WallService.add(postBeforeAdd2)
        val post3:Post = WallService.add(postBeforeAdd3)
        val comment:Comment = Comment(fromId = 1, text = "Первый!!!!111")
        //act
        val commentInPost:Comment = WallService.createComment(5, comment)

        }
}