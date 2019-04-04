import entities.Author;
import entities.Book;

import org.hibernate.Session;
import util.HibernateUtil;

import java.util.List;
import java.util.List.*;

public class Application {
    public static void main(String[] args) {
        Book book= new Book();
        System.out.println(getBooks());
    }
    public  static List<Book> getBooks (){
        Session session=HibernateUtil.getSessionFactory().openSession();
        List<Book> books= session.createQuery("select  b from Book b inner join b.author ").list();
        session.close();
        return books;
    }

}