package hu.vodafone.OIT.mail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.io.File;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.util.Map;

public class ExcelOps {
    public void excelExport(ArrayList<String> data) throws Exception {

        // workbook object
        XSSFWorkbook workbook = new XSSFWorkbook();

        // spreadsheet object
        XSSFSheet spreadsheet
                = workbook.createSheet(" Lost Device Data ");

        // creating a row object
        XSSFRow row;

        // This data needs to be written (Object[])
        Map<String, Object[]> studentData
                = new TreeMap<String, Object[]>();

        studentData.put(
                "1",
                new Object[]{"id", "ip", "hostname", "last_seen","Fanumber"});
        for (int i = 0; i < data.size(); i += 5) {
            studentData.put(String.valueOf(i+2), new Object[]{data.get(i), data.get(i + 1),
                    data.get(i + 2), data.get(i + 3),data.get(i+4)});

        }

        Set<String> keyid = studentData.keySet();

        int rowid = 0;


        for (String key : keyid) {

            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = studentData.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String) obj);
            }
        }


        FileOutputStream out;


        out = new FileOutputStream(
                new File("/home/retailupdateserer/Vodafone/lostdevices.xlsx"));
        workbook.write(out);
        out.close();


    }

}

