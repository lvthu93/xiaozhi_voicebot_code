package org.jsoup.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.helper.Validate;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

public class FormElement extends Element {
    private final Elements elements = new Elements();

    public FormElement(Tag tag, String str, Attributes attributes) {
        super(tag, str, attributes);
    }

    public FormElement addElement(Element element) {
        this.elements.add(element);
        return this;
    }

    public Elements elements() {
        return this.elements;
    }

    public List<Connection.KeyVal> formData() {
        String str;
        Element first;
        ArrayList arrayList = new ArrayList();
        Iterator it = this.elements.iterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();
            if (element.tag().isFormSubmittable() && !element.hasAttr("disabled")) {
                String attr = element.attr("name");
                if (attr.length() != 0) {
                    String attr2 = element.attr("type");
                    if ("select".equals(element.tagName())) {
                        Iterator it2 = element.select("option[selected]").iterator();
                        boolean z = false;
                        while (it2.hasNext()) {
                            arrayList.add(HttpConnection.KeyVal.create(attr, ((Element) it2.next()).val()));
                            z = true;
                        }
                        if (!z && (first = element.select("option").first()) != null) {
                            arrayList.add(HttpConnection.KeyVal.create(attr, first.val()));
                        }
                    } else if (!"checkbox".equalsIgnoreCase(attr2) && !"radio".equalsIgnoreCase(attr2)) {
                        arrayList.add(HttpConnection.KeyVal.create(attr, element.val()));
                    } else if (element.hasAttr("checked")) {
                        if (element.val().length() > 0) {
                            str = element.val();
                        } else {
                            str = "on";
                        }
                        arrayList.add(HttpConnection.KeyVal.create(attr, str));
                    }
                }
            }
        }
        return arrayList;
    }

    public void removeChild(Node node) {
        super.removeChild(node);
        this.elements.remove(node);
    }

    public Connection submit() {
        String str;
        Connection.Method method;
        if (hasAttr("action")) {
            str = absUrl("action");
        } else {
            str = baseUri();
        }
        Validate.notEmpty(str, "Could not determine a form action URL for submit. Ensure you set a base URI when parsing.");
        if (attr("method").toUpperCase().equals("POST")) {
            method = Connection.Method.POST;
        } else {
            method = Connection.Method.GET;
        }
        return Jsoup.connect(str).data((Collection<Connection.KeyVal>) formData()).method(method);
    }
}
