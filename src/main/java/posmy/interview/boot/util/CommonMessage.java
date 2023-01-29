package posmy.interview.boot.util;

import org.springframework.stereotype.Component;

@Component
public class CommonMessage {
    public final String BOOK_CREATE_SUCCESS = "The book created successfully.";
    public final String BOOK_ID_NOT_FOUND = "The book ID is not found.";
    public final String BOOK_UPDATE_SUCCESS = "The book updated.";
    public final String BOOK_RETURNED_SUCCESS = "The book has returned successfully.";
    public final String BOOK_IS_NOT_FOUND = "The book is not found.";
    public final String BOOK_IS_NOT_AVAILABLE = "The book is not available.";
    public final String BOOK_IS_STILL_AVAILABLE = "The book is still available.";
    public final String BOOK_BORROWED_SUCCESS = "The book has borrowed successfully.";
    public final String BOOK_DELETE_SUCCESS = "The book deleted successfully.";
    public final String USER_CREATE_SUCCESS = "The user created successfully.";
    public final String USER_UPDATE_SUCCESS = "The user updated successfully.";
    public final String USER_DELETE_SUCCESS = "The user deleted successfully.";
    public final String USER_DELETE_NO_PRIVILEGE = "No Privileges to delete the user.";
    public final String USER_IS_NOT_FOUND = "The user is not found.";
}
