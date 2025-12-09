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
    void testAddMemberInvalidEmail() {
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.addMember("John", "invalidEmail"));

        assertNotNull(ex.getMessage());
    }

    @Test
    void testAddBookInvalidYear() {
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.addBook("Book", "Author", -5));

        assertNotNull(ex.getMessage());
    }

    @Test
    void testBorrowBookNoStock() {
        Member m = new Member(1, "Test", "t@test.com");
        when(memberRepo.getMemberById(1)).thenReturn(m);

        Book b = new Book(10, "NoStockBook", "Author", 2000, false, 0);
        when(bookRepo.getBookById(10)).thenReturn(b);

        when(borrowRepo.getBorrowedBooksByMemberId(1)).thenReturn(List.of());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.borrowBook(1, 10));

        assertNotNull(ex.getMessage());
    }

    @Test
    void testBorrowBookMaxLimit() {
        Member m = new Member(1, "Test", "t@test.com");
        when(memberRepo.getMemberById(1)).thenReturn(m);

        Book b = new Book(10, "Book", "Author", 2000, false, 5);
        when(bookRepo.getBookById(10)).thenReturn(b);

        when(borrowRepo.getBorrowedBooksByMemberId(1))
                .thenReturn(List.of(
                        new BorrowedBook(1, 1, 1, LocalDateTime.now()),
                        new BorrowedBook(2, 1, 2, LocalDateTime.now()),
                        new BorrowedBook(3, 1, 3, LocalDateTime.now())
                ));

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.borrowBook(1, 10));

        assertNotNull(ex.getMessage());
    }

    @Test
    void testBorrowBookSuccessDecrementsQuantity() {
        Member m = new Member(1, "Test", "t@test.com");
        when(memberRepo.getMemberById(1)).thenReturn(m);

        Book b = new Book(10, "Book", "Author", 2000, false, 5);
        when(bookRepo.getBookById(10)).thenReturn(b);

        when(borrowRepo.getBorrowedBooksByMemberId(1)).thenReturn(List.of());

        assertDoesNotThrow(() -> service.borrowBook(1, 10));

        verify(borrowRepo, times(1)).addBorrowRecord(any(BorrowedBook.class));
        verify(bookRepo, times(1)).updateQuantity(10, 4); // 5 -> 4
    }

    @Test
    void testReturnBookSuccess() {
        // Stokta 5 adet olduğunu varsayalım, iade sonrası 6 bekleyeceğiz
        Book b = new Book(10, "Book", "Author", 2000, true, 5);
        when(bookRepo.getBookById(10)).thenReturn(b);

        // Act
        assertDoesNotThrow(() -> service.returnBook(1, 10));

        // Assert
        // ÖNEMLİ:
        // - V3 mantığında iade sonrası quantity artırılır (5 -> 6)
        // - Ayrıca ilgili borrow kaydı silinir
        verify(borrowRepo, times(1)).deleteBorrowRecord(1, 10);
        verify(bookRepo, times(1)).updateQuantity(10, 6);

        // updateBorrowedStatus çağrılmasını beklemiyoruz;
        // isBorrowed flag'inin ne zaman güncelleneceği V3'te daha esnek olabilir.
    }

    @Test
    void testSearchBooksEmptyKeywordThrows() {
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.searchBooks("  "));

        assertNotNull(ex.getMessage());
    }

    @Test
    void testListAllMembersEmptyThrows() {
        when(memberRepo.getAllMembers()).thenReturn(List.of());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.listAllMembers());

        assertNotNull(ex.getMessage());
    }
}
