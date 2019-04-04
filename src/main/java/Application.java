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
        //(String name, Integer page, Author author, Genre genre, Integer downloads)
//        System.out.println(
//                createBook(
//                    new Book("Harry Potter",350,new Author("J.K.Rowling"),getGenreByName("Fantasy"),5746)
//            )
//        );
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
//        deleteBook(getBookIdByName("Harry Potter"));
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
    public static Book createBook(Book book){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(book);
        session.getTransaction().commit();
        session.close();
        System.out.println("===========SaveOrUpdate===========\n......Создание объекта: \n..." + book + "\n......Объект успешно создан!!!");
        return book;
    }
    public static void deleteBook(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Book book = getBookById(id);
        session.delete(book);
        session.getTransaction().commit();
        session.close();
        System.out.println("===========Deleting==========\n.......Удаление объекта: \n..." + book + "\n......Объект успешно удален!!!");
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
    public static Author getAuthorById(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Author author = (Author)session.createQuery("select a from Author a where a.id = :id").setParameter("id", id);
        session.close();
        return author;
    }
    public static Author getAuthorByName(String name){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Author author =(Author)session.createQuery("select a from Author a where a.name = :name").setParameter("name",name);
        session.close();
        return author;
    }


}