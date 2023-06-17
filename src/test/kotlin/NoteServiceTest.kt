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
        //act
        val comment1: Comment = NoteService.createComment(Comment(), note.id)
        val comment2: Comment = NoteService.createComment(Comment(), note.id)
        val comment3: Comment = NoteService.createComment(Comment(), note.id)
        //assert
        assertEquals(1, comment1.id)
        assertEquals(2, comment2.id)
        assertEquals(3, comment3.id)
    }

    @Test
    fun deleteNote() {
        //arrange
        val note1: Note = NoteService.add(Note(ownerId = 1))
        val note2: Note = NoteService.add(Note(ownerId = 2))
        val note3: Note = NoteService.add(Note(ownerId = 3))
        val comment1: Comment = NoteService.createComment(Comment(), note2.id)
        val comment2: Comment = NoteService.createComment(Comment(), note2.id)
        val comment3: Comment = NoteService.createComment(Comment(), note2.id)
        //act
       val isDeleted: Boolean = NoteService.delete(2)
        //assert
        assertTrue(isDeleted)
        assertEquals(0, NoteService.getComments().size)
    }

    @Test
    fun deleteCommentMarker() {
        //arrange
        val note1: Note = NoteService.add(Note(ownerId = 1))
        val note2: Note = NoteService.add(Note(ownerId = 2))
        val note3: Note = NoteService.add(Note(ownerId = 3))
        val comment1: Comment = NoteService.createComment(Comment(), note2.id)
        val comment2: Comment = NoteService.createComment(Comment(), note2.id)
        val comment3: Comment = NoteService.createComment(Comment(), note2.id)
        //act
        NoteService.deleteComment(2)
        //assert
        assertTrue(comment2.isDeleted)
    }

    @Test
    fun editNote() {
        //arrange
        val note1: Note = NoteService.add(Note(ownerId = 1))
        val note2: Note = NoteService.add(Note(ownerId = 2))
        val note3: Note = NoteService.add(Note(ownerId = 3))
        //act
        NoteService.edit(2, "Новый текст к заметке 2")
        //
        assertEquals("Новый текст к заметке 2", NoteService.getById(2).text)
    }

    @Test
    fun editCommentNote() {
        //arrange
        val note1: Note = NoteService.add(Note(ownerId = 1))
        val note2: Note = NoteService.add(Note(ownerId = 2))
        val note3: Note = NoteService.add(Note(ownerId = 3))
        val comment1: Comment = NoteService.createComment(Comment(), note2.id)
        val comment2: Comment = NoteService.createComment(Comment(), note2.id)
        val comment3: Comment = NoteService.createComment(Comment(), note2.id)
        //act
        NoteService.editComment(2, "Новый текст комментария 2 к заметке")
        //assert
        assertEquals("Новый текст комментария 2 к заметке", NoteService.getComments()[1].text)
    }

    @Test(expected = SomethingWrongException::class)
    fun editRemoveCommentNote() {
        //arrange
        val note1: Note = NoteService.add(Note(ownerId = 1))
        val note2: Note = NoteService.add(Note(ownerId = 2))
        val note3: Note = NoteService.add(Note(ownerId = 3))
        val comment1: Comment = NoteService.createComment(Comment(), note2.id)
        val comment2: Comment = NoteService.createComment(Comment(), note2.id)
        val comment3: Comment = NoteService.createComment(Comment(), note2.id)
        NoteService.deleteComment(2)
        //act
        NoteService.editComment(2, "Новый текст комментария 2 к заметке")
    }

    @Test
    fun getListOfNotes() {
        //arrange
        val note1: Note = NoteService.add(Note(ownerId = 1))
        val note2: Note = NoteService.add(Note(ownerId = 2))
        val note3: Note = NoteService.add(Note(ownerId = 3))
        //act
        val listOfNotes = NoteService.get()
        //assert
        assertEquals(3, listOfNotes.size)
    }

    @Test
    fun getNoteById() {
        //arrange
        val note1: Note = NoteService.add(Note(ownerId = 1))
        val note2: Note = NoteService.add(Note(ownerId = 2))
        val note3: Note = NoteService.add(Note(ownerId = 3))
        //act
        val noteAssert =  NoteService.getById(2)
        //assert
        assertEquals(noteAssert, note2)
    }

    @Test(expected = SomethingWrongException::class)
    fun getNoteByIdException() {
        //arrange
        val note1: Note = NoteService.add(Note(ownerId = 1))
        val note2: Note = NoteService.add(Note(ownerId = 2))
        val note3: Note = NoteService.add(Note(ownerId = 3))
        //act
        val noteAssert =  NoteService.getById(4)
    }

    @Test
    fun getListOfComments() {
        //arrange
        val note1: Note = NoteService.add(Note(ownerId = 1))
        val note2: Note = NoteService.add(Note(ownerId = 2))
        val note3: Note = NoteService.add(Note(ownerId = 3))
        val comment1: Comment = NoteService.createComment(Comment(), note1.id)
        val comment2: Comment = NoteService.createComment(Comment(), note1.id)
        val comment3: Comment = NoteService.createComment(Comment(), note1.id)
        val comment4: Comment = NoteService.createComment(Comment(), note2.id)
        val comment5: Comment = NoteService.createComment(Comment(), note2.id)
        val comment6: Comment = NoteService.createComment(Comment(), note2.id)
        val comment7: Comment = NoteService.createComment(Comment(), note3.id)
        val comment8: Comment = NoteService.createComment(Comment(), note3.id)
        val comment9: Comment = NoteService.createComment(Comment(), note3.id)
        //act
       val listOfComments = NoteService.getComments()
        //assert
        assertEquals(9, listOfComments.size)

    }

    @Test
    fun getListOfCommentsWithoutDeleted() {
        //arrange
        val note1: Note = NoteService.add(Note(ownerId = 1))
        val note2: Note = NoteService.add(Note(ownerId = 2))
        val note3: Note = NoteService.add(Note(ownerId = 3))
        val comment1: Comment = NoteService.createComment(Comment(), note1.id)
        val comment2: Comment = NoteService.createComment(Comment(), note1.id)
        val comment3: Comment = NoteService.createComment(Comment(), note1.id)
        val comment4: Comment = NoteService.createComment(Comment(), note2.id)
        val comment5: Comment = NoteService.createComment(Comment(), note2.id)
        val comment6: Comment = NoteService.createComment(Comment(), note2.id)
        val comment7: Comment = NoteService.createComment(Comment(), note3.id)
        val comment8: Comment = NoteService.createComment(Comment(), note3.id)
        val comment9: Comment = NoteService.createComment(Comment(), note3.id)
        //act
        NoteService.deleteComment(2)
        NoteService.deleteComment(5)
        NoteService.deleteComment(8)
        val listOfComments = NoteService.getComments()
        //assert
        assertEquals(6, listOfComments.size)

    }

    @Test
    fun restoreComment() {
        val note1: Note = NoteService.add(Note(ownerId = 1))
        val note2: Note = NoteService.add(Note(ownerId = 2))
        val note3: Note = NoteService.add(Note(ownerId = 3))
        val comment1: Comment = NoteService.createComment(Comment(), note1.id)
        val comment2: Comment = NoteService.createComment(Comment(), note1.id)
        val comment3: Comment = NoteService.createComment(Comment(), note1.id)
        val comment4: Comment = NoteService.createComment(Comment(), note2.id)
        val comment5: Comment = NoteService.createComment(Comment(), note2.id)
        val comment6: Comment = NoteService.createComment(Comment(), note2.id)
        val comment7: Comment = NoteService.createComment(Comment(), note3.id)
        val comment8: Comment = NoteService.createComment(Comment(), note3.id)
        val comment9: Comment = NoteService.createComment(Comment(), note3.id)
        //act
        NoteService.deleteComment(2)
        val sizeBeforeRestore = NoteService.getComments().size
        NoteService.restoreComment(2)
        //assert
        assertEquals((sizeBeforeRestore+1), NoteService.getComments().size)
    }

    @Test(expected = SomethingWrongException::class)
    fun restoreCommentWithException1() {
        val note1: Note = NoteService.add(Note(ownerId = 1))
        val note2: Note = NoteService.add(Note(ownerId = 2))
        val note3: Note = NoteService.add(Note(ownerId = 3))
        val comment1: Comment = NoteService.createComment(Comment(), note1.id)
        val comment2: Comment = NoteService.createComment(Comment(), note1.id)
        val comment3: Comment = NoteService.createComment(Comment(), note1.id)
        val comment4: Comment = NoteService.createComment(Comment(), note2.id)
        val comment5: Comment = NoteService.createComment(Comment(), note2.id)
        val comment6: Comment = NoteService.createComment(Comment(), note2.id)
        val comment7: Comment = NoteService.createComment(Comment(), note3.id)
        val comment8: Comment = NoteService.createComment(Comment(), note3.id)
        val comment9: Comment = NoteService.createComment(Comment(), note3.id)
        //act
        NoteService.restoreComment(2)
    }

    @Test(expected = SomethingWrongException::class)
    fun restoreCommentWithException2() {
        val note1: Note = NoteService.add(Note(ownerId = 1))
        val note2: Note = NoteService.add(Note(ownerId = 2))
        val note3: Note = NoteService.add(Note(ownerId = 3))
        val comment1: Comment = NoteService.createComment(Comment(), note1.id)
        val comment2: Comment = NoteService.createComment(Comment(), note1.id)
        val comment3: Comment = NoteService.createComment(Comment(), note1.id)
        val comment4: Comment = NoteService.createComment(Comment(), note2.id)
        val comment5: Comment = NoteService.createComment(Comment(), note2.id)
        val comment6: Comment = NoteService.createComment(Comment(), note2.id)
        val comment7: Comment = NoteService.createComment(Comment(), note3.id)
        val comment8: Comment = NoteService.createComment(Comment(), note3.id)
        val comment9: Comment = NoteService.createComment(Comment(), note3.id)
        //act
        NoteService.restoreComment(50)
    }

}