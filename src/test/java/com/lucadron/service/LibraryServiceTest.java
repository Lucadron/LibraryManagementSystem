package com.lucadron.service;

import com.lucadron.i18n.LanguageManager;
import com.lucadron.model.Book;
import com.lucadron.model.BorrowedBook;
import com.lucadron.model.Member;
import com.lucadron.repository.BookRepository;
import com.lucadron.repository.BorrowedBookRepository;
import com.lucadron.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LibraryServiceTest {

    private LibraryService service;
    private BookRepository bookRepo;
    private MemberRepository memberRepo;
    private BorrowedBookRepository borrowRepo;

    @BeforeEach
    void setup() throws Exception {
        LanguageManager.setLanguage(LanguageManager.SupportedLanguage.EN);

        service = new LibraryService();

        bookRepo = Mockito.mock(BookRepository.class);
        memberRepo = Mockito.mock(MemberRepository.class);
        borrowRepo = Mockito.mock(BorrowedBookRepository.class);

        injectPrivateField(service, "bookRepo", bookRepo);
        injectPrivateField(service, "memberRepo", memberRepo);
        injectPrivateField(service, "borrowRepo", borrowRepo);
    }

    private void injectPrivateField(Object target, String fieldName, Object mock) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, mock);
    }

    @Test
    void testAddMemberSuccess() {
        Member mockMember = new Member(1, "John", "john@example.com");

        when(memberRepo.addMember(any(Member.class))).thenReturn(mockMember);

        Member created = service.addMember("John", "john@example.com");

        assertEquals(1, created.getId());
        assertEquals("John", created.getName());
        verify(memberRepo, times(1)).addMember(any(Member.class));
    }

    @Test
    void testAddBookInvalidYear() {
        assertThrows(RuntimeException.class, () -> service.addBook("Book", "Author", -5));
    }

    @Test
    void testBorrowBookAlreadyBorrowed() {
        Member m = new Member(1, "Test", "t@test.com");
        Book b = new Book(10, "TestBook", "Author", 2000, true);

        when(memberRepo.getMemberById(1)).thenReturn(m);
        when(bookRepo.getBookById(10)).thenReturn(b);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.borrowBook(1, 10));

        assertTrue(ex.getMessage().contains("Book is already borrowed"));
    }

    @Test
    void testBorrowBookMaxLimit() {
        Member m = new Member(1, "Test", "t@test.com");
        Book b = new Book(10, "Book", "Author", 2000, false);

        when(memberRepo.getMemberById(1)).thenReturn(m);
        when(bookRepo.getBookById(10)).thenReturn(b);
        when(borrowRepo.getBorrowedBooksByMemberId(1))
                .thenReturn(List.of(
                        new BorrowedBook(1, 1, 1, LocalDateTime.now()),
                        new BorrowedBook(2, 1, 2, LocalDateTime.now()),
                        new BorrowedBook(3, 1, 3, LocalDateTime.now())
                ));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.borrowBook(1, 10));

        assertTrue(ex.getMessage().contains("maximum number of books"));
    }

    @Test
    void testReturnBookSuccess() {
        Book b = new Book(10, "Book", "Author", 2000, true);

        when(bookRepo.getBookById(10)).thenReturn(b);

        assertDoesNotThrow(() -> service.returnBook(1, 10));

        verify(borrowRepo, times(1)).deleteBorrowRecord(1, 10);
        verify(bookRepo, times(1)).updateBorrowedStatus(10, false);
    }
}
