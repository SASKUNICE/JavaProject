package com.appli.utils;

import com.google.gson.Gson;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.servlet.http.HttpServletResponse;

import java.io.OutputStream;
import java.sql.*;
import java.util.*;
import java.util.List; // Correct pour les listes standard Java



public class ExportTo {

    // Export vers CSV
    public static void exportToCSV(ResultSet rs, HttpServletResponse response) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"export.csv\"");
        OutputStream out = response.getOutputStream();

        // Écrire les entêtes
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            out.write(metaData.getColumnName(i).getBytes());
            if (i < columnCount) out.write(",".getBytes());
        }
        out.write("\n".getBytes());

        // Écrire les lignes
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                out.write(rs.getString(i).getBytes());
                if (i < columnCount) out.write(",".getBytes());
            }
            out.write("\n".getBytes());
        }

        out.flush();
        out.close();
    }

    // Export vers JSON
    public static void exportToJSON(ResultSet rs, HttpServletResponse response) throws Exception {
        response.setContentType("application/json");
        response.setHeader("Content-Disposition", "attachment; filename=\"export.json\"");
        OutputStream out = response.getOutputStream();

        List<Map<String, String>> rows = new ArrayList<>();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            Map<String, String> row = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                row.put(metaData.getColumnName(i), rs.getString(i));
            }
            rows.add(row);
        }

        Gson gson = new Gson();
        String json = gson.toJson(rows);
        out.write(json.getBytes());
        out.flush();
        out.close();
    }

    // Export vers XML
    public static void exportToXML(ResultSet rs, HttpServletResponse response) throws Exception {
        response.setContentType("application/xml");
        response.setHeader("Content-Disposition", "attachment; filename=\"export.xml\"");
        OutputStream out = response.getOutputStream();

        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<rows>\n".getBytes());

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            out.write("<row>\n".getBytes());
            for (int i = 1; i <= columnCount; i++) {
                out.write(("<" + metaData.getColumnName(i) + ">").getBytes());
                out.write(rs.getString(i).getBytes());
                out.write(("</" + metaData.getColumnName(i) + ">\n").getBytes());
            }
            out.write("</row>\n".getBytes());
        }
        out.write("</rows>".getBytes());

        out.flush();
        out.close();
    }

    // Export vers XLS
    public static void exportToXLS(ResultSet rs, HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"export.xlsx\"");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Export");

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Créer les en-têtes
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columnCount; i++) {
            headerRow.createCell(i).setCellValue(metaData.getColumnName(i + 1));
        }

        // Remplir les données
        int rowIndex = 1;
        while (rs.next()) {
            Row row = sheet.createRow(rowIndex++);
            for (int i = 0; i < columnCount; i++) {
                row.createCell(i).setCellValue(rs.getString(i + 1));
            }
        }

        OutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
        out.flush();
        out.close();
    }

    // Export vers PDF
    public static void exportToPDF(ResultSet rs, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"export.pdf\"");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Ajouter une table
        PdfPTable table = new PdfPTable(rs.getMetaData().getColumnCount());
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Ajouter les en-têtes
        for (int i = 1; i <= columnCount; i++) {
            table.addCell(metaData.getColumnName(i));
        }

        // Ajouter les lignes
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                table.addCell(rs.getString(i));
            }
        }

        document.add(table);
        document.close();
    }
}
