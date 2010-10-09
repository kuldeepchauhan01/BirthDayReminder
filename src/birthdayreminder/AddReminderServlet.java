package birthdayreminder;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


@SuppressWarnings("serial")
public class AddReminderServlet extends HttpServlet {
	
		private static final Logger log = Logger.getLogger(AddReminderServlet.class.getName());

	    public void doPost(HttpServletRequest req, HttpServletResponse resp)
	                throws IOException {
	        UserService userService = UserServiceFactory.getUserService();
	        User user = userService.getCurrentUser();

	        String content = req.getParameter("content");
	        if (content == null) {
	            content = "(No greeting)";
	        }
	        Date date = new Date();
	        Reminder reminder = new Reminder(user.getUserId(), content, date);

	        PersistenceManager pm = PMF.get().getPersistenceManager();
	        try {
	            pm.makePersistent(reminder);
	        } finally {
	            pm.close();
	        }

	        resp.sendRedirect("/login.jsp");
	}
}
