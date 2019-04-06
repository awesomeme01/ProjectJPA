import entities.Author;
import entities.Book;

import entities.Genre;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.swing.*;
import java.util.List;
import java.util.List.*;

public class Application {
    public static void main(String[] args) {
        System.out.println("\n=========== GETTING ALL BOOKS ============");
        System.out.println(getBooks());

        System.out.println("\n=========== GETTING BOOKS BY NAME (name = Harry Potter)============");
        System.out.println(getBooksByName("Harry Potter"));

        System.out.println("\n=========== GETTING BOOKS BY GENRE (genre = Fantasy)============");
        System.out.println(getBooksByGenre("Fantasy"));

        System.out.println("\n=========== GETTING BOOKS BY ID (id = 3)============");
        System.out.println(getBookById(3));

        System.out.println("\n=========== GETTING BOOKS BY AUTHOR (author = Джон Вердон)============");
        System.out.println(getBooksByAuthor("Джон Вердон"));

        System.out.println("\n=========== GETTING BOOKS MOST POPULAR (number = 3)============");
        System.out.println(getMostPopular(3));

        System.out.println("\n=========== GETTING BOOK.ID by NAME (name = Загадай число)============");
        System.out.println(getBookIdByName("Загадай число"));

        System.out.println("\n=========== GETTING GENRE by ID (id = 1)============");
        System.out.println(getGenreById(1));

        System.out.println("\n=========== GETTING GENRE by NAME (name = Fantasy)============");
        System.out.println(getGenreByName("Fantasy"));

        System.out.println("\n=========== GETTING AUTHOR by ID(id = 1)============");
        System.out.println(getAuthorById(1));

        System.out.println("\n=========== GETTING AUTHOR by NAME(name = Джон Вердон)============");
        System.out.println(getAuthorByName("Джон Вердон"));

        System.out.println("\n=========== UPDATING BOOK ============");
        Book book1 = getBookById(1);
        book1.setPage(400);
        System.out.println(createOrUpdateBook(book1));

        System.out.println("\n=========== CREATING AUTHOR ============");
        System.out.println(createAuthor(
                new Author(5,"J.K.Rowling")
        ));
        System.out.println("\n=========== CREATING BOOK ============");
        //(String name, Integer page, Author author, Genre genre, Integer downloads)
        System.out.println(createBook(
                new Book(5,"Harry Potter", 400, getAuthorByName("J.K.Rowling"),getGenreByName("Fantasy"),4536)
        ));
        System.out.println("\n=========== CREATING GENRE ============");
        System.out.println(createGenre(
                new Genre(6,"Adventure")
        ));

        System.out.println("\n=========== GET ALL BOOKS ============");
        for (Book book : getBooks()){
            System.out.println(book);
        }
        //DELETING A BOOK BY ID
        System.out.println("\n=========== DELETING A BOOK BY ID(id = 5) =============");
        deleteBook(5);

        System.out.println("\n=========== GET ALL BOOKS ============");
        for (Book book : getBooks()){
            System.out.println(book);
        }

        System.out.println("\n=========== GET ALL AUTHORs ============");
        for (Author author : getAuthors()){
            System.out.println(author);
        }
        //DELETING A AUTHOR BY ID
        System.out.println("\n=========== DELETING AUTHOR BY ID(id = 5) =============");
        deleteAuthor(5);

        System.out.println("\n=========== GET ALL AUTHORs ============");
        for (Author author : getAuthors()){
            System.out.println(author);
        }

        System.out.println("\n=========== GET ALL GENREs ============");
        for (Genre genre: getGenres()){
            System.out.println(genre);
        }
        //DELETING A GENRE BY ID
        System.out.println("\n=========== DELETING GENRE BY ID(id = 6) =============");
        deleteGenre(6);

        System.out.println("\n=========== GET ALL GENREs ============");
        for (Genre genre: getGenres()){
            System.out.println(genre);
        }


    }
    public  static List<Book> getBooks (){//Simply get all books
        Session session=HibernateUtil.getSessionFactory().openSession();
        List<Book> books= session.createQuery("select b from Book b").list();
        session.close();
        return books;
    }
    public static List<Book> getBooksByName(String name){//what is the name of the book?
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Book> books = session.createQuery("select b from Book b where name = :name").setParameter("name",name).list();
        session.close();
        return books;
    }
    public static List<Book> getBooksByGenre(String genre){//by which genre?
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Book> books = session.createQuery("select b from Book b where b.genre.name = :genre").setParameter("genre",genre).list();
        session.close();
        return books;
    }
    public static List<Book> getBooksByAuthor(String author){//by which author?
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Book> books = session.createQuery("select b from Book b where b.author.name = :author").setParameter("author", author).list();
        session.close();
        return books;
    }
    public static List<Book> getMostPopular(int number){//how many books?
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Book> books = session.createQuery("select b from Book b order by b.downloads desc").setMaxResults(number).list();
        session.close();
        return books;
    }
    public static Book getBookById(int id){//which id?
        Session session = HibernateUtil.getSessionFactory().openSession();
        Book book = (Book)session.createQuery("select b from Book b where b.id = :id").setParameter("id", id).getSingleResult();
        session.close();
        return book;
    }
    public static Integer getBookIdByName(String name){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Integer id = (Integer)session.createQuery("select b.id from Book b where b.name = :name").setParameter("name", name).getSingleResult();
        session.close();
        return id;
    }
    public static List<Genre> getGenres(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Genre> genres = session.createQuery("select g from Genre g").list();
        session.close();
        return genres;
    }
    public static Genre getGenreById(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Genre genre = (Genre)session.createQuery("select g from entities.Genre g where g.id = :id").setParameter("id",id).getSingleResult();
        session.close();
        return genre;
    }
    public static Genre getGenreByName(String name){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Genre genre = (Genre)session.createQuery("select g from entities.Genre g where g.name = :name").setParameter("name",name).getSingleResult();
        session.close();
        return genre;
    }
    public static List<Author> getAuthors(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Author> authors = session.createQuery("select a from Author a").list();
        session.close();
        return authors;
    }
    public static Author getAuthorById(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Author author = (Author)session.createQuery("select a from Author a where a.id = :id").setParameter("id", id).getSingleResult();
        session.close();
        return author;
    }
    public static Author getAuthorByName(String name){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Author author =(Author)session.createQuery("select a from Author a where a.name = :name").setParameter("name",name).getSingleResult();
        session.close();
        return author;
    }
    //          --------------------------------------------                   //

    //Create or Update Book
    public static Book createOrUpdateBook(Book book){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(book);
        session.getTransaction().commit();
        session.close();
        System.out.println("===========SaveOrUpdate===========\n......Создание или обновление объекта: \n..." + book + "\n......Операция выполнена успешно!!!");
        return book;
    }

    //Create Book
    public static Book createBook(Book book){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(book);
        session.getTransaction().commit();
        session.close();
        System.out.println("Book is SUCCESSFULLY created!! --->\n" + book);
        return book;
    }

    //Delete Book
    public static void deleteBook(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Book book = getBookById(id);
        session.delete(book);
        session.getTransaction().commit();
        session.close();
        System.out.println("===========Deleting==========\n.......Удаление объекта: \n..." + book + "\n......Объект успешно удален!!!");
    }
    //Create AUTHOR
    public static Author createAuthor(Author author){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(author);
        session.getTransaction().commit();
        session.close();
        System.out.println("Author was SUCCESSFULLY created ---> \n" + author);
        return author;
    }
    //Create or Update AUTHOR
    public static Author createOrUpdateAuthor(Author author){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(author);
        session.getTransaction().commit();
        session.close();
        System.out.println("Author was SUCCESSFULLY created or updated---> \n" + author);
        return author;
    }
    //Create GENRE
    public static Genre createGenre(Genre genre){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(genre);
        session.getTransaction().commit();
        session.close();
        System.out.println("Genre was SUCCESSFULLY create --->\n" + genre);
        return genre;
    }
    //Create or Update GENRE
    public static Genre createOrUpdateGenre(Genre genre){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(genre);
        session.getTransaction().commit();
        session.close();
        System.out.println("Genre was SUCCESSFULLY create or updated--->\n" + genre);
        return genre;
    }
    //Deleting AUTHOR by id
    public static void deleteAuthor(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Author author = getAuthorById(id);
        session.delete(author);
        session.getTransaction().commit();
        session.close();
        System.out.println("===========Deleting==========\n.......Удаление объекта: \n..." + author + "\n......Объект успешно удален!!!");
    }
    //Deleting GENRE by id
    public static void deleteGenre(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Genre genre = getGenreById(id);
        session.delete(genre);
        session.getTransaction().commit();
        session.close();
        System.out.println("===========Deleting==========\n.......Удаление объекта: \n..." + genre + "\n......Объект успешно удален!!!");
    }

}