package ru.job4j.servlets;

import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeleteCandidateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Store.instOf().deleteCandidateById(Integer.parseInt(id));
        File repository = new File("c:\\images\\");
        if (repository.isDirectory()) {
            for (File file : repository.listFiles()) {
                String fileName = file.getName().substring(0, file.getName().indexOf('.'));
                if (fileName.equals(id)) {
                    file.delete();
                    break;
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/candidate.do");
    }
}
