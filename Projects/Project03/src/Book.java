/**
 * Represents a book record containing bibliographical data.
 *
 * @param isbn            the International Standard Book Number
 * @param authors         the authors of the book
 * @param publicationYear the year of publication
 * @param originalTitle   the original title of the book
 * @param title           the title of the book
 * @param averageRating   the average user rating
 */
public record Book(
        String isbn,
        String authors,
        int publicationYear,
        String originalTitle,
        String title,
        double averageRating
) {
}