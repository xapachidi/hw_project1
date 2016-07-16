package kz.stqa.pft.addressbook.tests;

import kz.stqa.pft.addressbook.model.ContactData;
import kz.stqa.pft.addressbook.model.GroupData;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by xeniya on 6/22/16.
 */
public class ContactEmailTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions(){
        if (! app.contact().isThereContact()) {
            if (!app.group().isThereAGroup()) {
                app.goTo().gotoGroupPage();
                app.group().createGroup(new GroupData().withName("test1"));
                app.goTo().gotoContactPage();
            }
            app.contact().createContact(new ContactData().withFirst_name("Петр").withSecond_name("Петрович").withLast_name("Кузьмин").withCompany("ложки.ком").withPhone("+89654123654").withCompany("test1"));
        }
    }

    @Test
    public void testContactEmail(){
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact.getAllEmails(), equalTo(app.contact().mergeEmails(contactInfoFromEditForm)));



    }

    public static String cleaned(String email){
        return email.replaceAll("\\s", "");
    }
}
