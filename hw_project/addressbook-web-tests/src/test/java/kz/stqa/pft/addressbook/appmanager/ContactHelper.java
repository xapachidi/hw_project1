package kz.stqa.pft.addressbook.appmanager;

import kz.stqa.pft.addressbook.model.ContactData;
import kz.stqa.pft.addressbook.model.Contacts;
import kz.stqa.pft.addressbook.model.GroupData;
import kz.stqa.pft.addressbook.tests.ContactEmailTests;
import kz.stqa.pft.addressbook.tests.ContactPhoneTests;
import kz.stqa.pft.addressbook.tests.DetailContactTests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.openqa.selenium.NoSuchElementException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xeniya on 6/2/16.
 */
public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirst_name());
        type(By.name("middlename"), contactData.getSecond_name());
        type(By.name("lastname"), contactData.getLast_name());
        type(By.name("company"), contactData.getCompany());
        type(By.name("home"), contactData.getPhone());

        if (creation){
            if (contactData.getGroups().size() > 0) {
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
            }
            } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }


    public void selectContact(int id) {
        wd.findElements(By.name("selected[]")).get(id).click();
        //click(By.name("selected[]"));
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void initContactModification(int id) {
        wd.findElement(By.cssSelector("a[href='edit.php?id="+id+"']")).click();
    }

    public void initContactDetail(int id) {
        wd.findElement(By.cssSelector("a[href='view.php?id="+id+"']")).click();
    }

    public void buttonOkClick() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void buttonUpdateClick() {
        click(By.xpath("//div[@id='content']/form[1]/input[22]"));
    }

    public void initContactDeletion() {
        click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    }

    public void initOkDeletion() {
        wd.switchTo().alert().accept();
    }


    public boolean isThereContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public void returnToContactPage() {
        //click(By.cssSelector("div.msgbox"));
        click(By.linkText("home page"));
    }

    public void createContact(ContactData contact) {
        fillContactForm(contact, true);
        buttonOkClick();
        returnToContactPage();
    }

    public void modifyContact(ContactData contact) {
        selectContactById(contact.getId());
        initContactModification(contact.getId());
        fillContactForm(contact, false);
        buttonUpdateClick();
        returnToContactPage();
    }

    public Contacts all() {
        Contacts contacts = new Contacts();
        List<WebElement> rows = wd.findElements(By.name("entry"));
        for (WebElement row : rows){
            List<WebElement> cells = row.findElements(By.tagName("td"));
            int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));
            String first_name = cells.get(2).getText();
            String last_name = cells.get(1).getText();
            String allEmails = cells.get(4).getText();
            String allPhones = cells.get(5).getText();
            contacts.add(new ContactData().withId(id).withFirst_name(first_name)
                    .withLast_name(last_name).withAllEmails(allEmails).withAllPhones(allPhones));
        }
        return contacts;

    }

    public String info(ContactData contact){
        initContactDetail(contact.getId());
        String inform  = wd.findElement(By.id("content")).getText();
        //Contacts contactDetail = new Contacts();

        return inform;
    }

    public void deleteContact(ContactData contact) {
        selectContactById(contact.getId());
        initContactDeletion();
        initOkDeletion();
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModification(contact.getId());
        String first_name = wd.findElement(By.name("firstname")).getAttribute("value");
        String second_name = wd.findElement(By.name("middlename")).getAttribute("value");
        String last_name = wd.findElement(By.name("lastname")).getAttribute("value");
        String company = wd.findElement(By.name("company")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobilePhone = wd.findElement(By.name("mobile")).getAttribute("value");
        String workPhone = wd.findElement(By.name("work")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirst_name(first_name)
                .withSecond_name(second_name).withLast_name(last_name).withCompany(company)
                .withEmail(email).withEmail2(email2).withEmail3(email3)
                .withPhone(home).withMobilePhone(mobilePhone).withWorkPhone(workPhone);

    }


    public String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getPhone(), contact.getMobilePhone(), contact.getWorkPhone())
                .stream().filter((s)->!s.equals(""))
                .map(ContactPhoneTests::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s)->!s.equals(""))
                .map(ContactEmailTests::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public String mergeFIO(ContactData contact) {
        String infoFIO = Arrays.asList(contact.getFirst_name(), contact.getSecond_name(), contact.getLast_name())
                .stream().filter((s)->!s.equals(""))
                .collect(Collectors.joining(" "));
        return infoFIO;

    }

    public String mergePhone(ContactData contact){
        //String HP = "H: " + contact.getPhone();
        //String MP = "M: " + contact.getMobilePhone();
        //String WP = "P: " + contact.getWorkPhone();

        return Arrays.asList("H: " + contact.getPhone(), "M: " + contact.getMobilePhone(), "W: " + contact.getWorkPhone())
                .stream().filter((s)-> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }

    public String mergeEmails1(ContactData contact){
        String  email1 = " (www." + contact.getEmail().substring(contact.getEmail().indexOf("@")+1) + ")";
        String email2 = " (www." + contact.getEmail2().substring(contact.getEmail2().indexOf("@")+1) + ")";
        String  email3 = " (www." + contact.getEmail3().substring(contact.getEmail3().indexOf("@")+1) + ")";

        return Arrays.asList(contact.getEmail() + email1, contact.getEmail2() + email2, contact.getEmail3()+ email3)
                .stream().filter((s)-> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }

    public String mergeInfo(ContactData contact){
        String infoAll = Arrays.asList(mergeFIO(contact), mergePhone(contact), mergeEmails1(contact))
                .stream().filter((s)-> !s.equals(""))
                .collect(Collectors.joining("\n\n"));
        return infoAll;
    }

}