package controllers.admin.with;

import java.util.Map;
import play.mvc.Before;
import play.mvc.Controller;
import controllers.CRUD.ObjectType;
import controllers.CRUD.ObjectType.ObjectField;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class CRUDSearch extends Controller {

	@Before//(only={"list"})
	public static void search() {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
		final Map<String, String> search = new HashMap<String, String>();
		final StringBuilder sb = new StringBuilder();
		final Map<String, String> pr = params.allSimple();
		int i = 0;
		for(String key : pr.keySet()) {
			if(key.startsWith("search_") && !"".equals(pr.get(key).trim())) {
				final String fieldName = key.substring(7);
				final ObjectField field = type.getField(fieldName);
				final String value = pr.get(key);
				if(field != null) { // todo parselong fix !
					final StringBuilder whereItem = new StringBuilder();
					try {
						if(i > 0)
							whereItem.append(" AND ");
						whereItem.append(fieldName);
						if(field.type.equals("relation"))
							whereItem.append("=").append(Long.parseLong(value));
						else if(field.type.equals("boolean"))
							whereItem.append("=").append(Integer.parseInt(value) > 0 ? "true" : "false");
						else if(field.type.equals("date")) {
							final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							final Date from = format.parse(value);
							final Date to = new Date(from.getTime() + (1000*60*60*24));
							whereItem.append(">='").append(format.format(from)).append("'").append(" AND ").append(fieldName).append("<'").append(format.format(to)).append("'");
						} else
							whereItem.append(" LIKE '%").append(value.replace("'", "")).append("%'");
						search.put(fieldName, value);
						i++;
						sb.append(whereItem.toString());
					} catch(NumberFormatException ex) {}
					catch(ParseException ex) {}
				}
			}
		}
		final String where = sb.toString();
		if(!"".equals(where))
			request.args.put("where", where);
		renderArgs.put("crudSearch", search);
		System.out.println(where);
	}
}
