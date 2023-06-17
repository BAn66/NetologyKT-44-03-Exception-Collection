import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class NoteServiceTest {
    @Before
    fun clearBeforeTest() {
        NoteService.clear()
    }
    @Test
    fun addNote() {
        //arrange
        val noteBeforeAdd1 = Note(ownerId = 1)
        val noteBeforeAdd2 = Note(ownerId = 2)
        val noteBeforeAdd3 = Note(ownerId = 3)
        //act
        val note1: Note = NoteService.add(noteBeforeAdd1)
        val note2: Note = NoteService.add(noteBeforeAdd2)
        val note3: Note = NoteService.add(noteBeforeAdd3)
        //assert
        assertEquals(1, note1.id)
        assertEquals(2, note2.id)
        assertEquals(3, note3.id)
    }

    @Test
    fun createNewComment() {
        //arrange
        val noteBeforeAdd = Note(ownerId = 1)
        val note: Note = NoteService.add(noteBeforeAdd)
        val commentBeforeCreate : Comment = Comment()
        //act
        val comment: Comment = NoteService.createComment(commentBeforeCreate, note.id)
        //assert
        assertEquals(1, comment.id)
    }

    @Test
    fun delete() {
    }

    @Test
    fun deleteComment() {
    }

    @Test
    fun edit() {
    }

    @Test
    fun editComment() {
    }

    @Test
    fun get() {
    }

    @Test
    fun getById() {
    }

    @Test
    fun getComments() {
    }

    @Test
    fun restoreComment() {
    }
}