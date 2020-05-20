package com.sdut.onlinejudge;


import com.sdut.onlinejudge.utils.DateUtil;
import com.sdut.onlinejudge.utils.ExcelUtils;
import com.sdut.onlinejudge.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

//@SpringBootTest
public class OnlinejudgeApplicationTests {


    @Test
    public void test01() {
        String token = JwtUtils.createJWT(UUID.randomUUID().toString(), "11111", 10 * 60 * 1000);
        SecretKey Key = JwtUtils.generalKey();
        System.out.println(token);
        JwtParser jwtParser = Jwts.parser().setSigningKey(Key);


        Claims body = jwtParser.parseClaimsJws(token).getBody();
        String subject = body.getSubject();
        System.out.println(body);

    }

    @Test
    public void test02() {
        File file = new File("C:\\Users\\Devhui\\Documents\\Tencent Files\\501966782\\FileRecv\\辅导员题目\\多选题.xlsx");
        try {
            XSSFWorkbook wb = new XSSFWorkbook(file);
            int sheets = wb.getNumberOfSheets();
            //只读取第一个sheet
            XSSFSheet sheetAt = wb.getSheetAt(0);
            //这个表示当前sheet有多少行数据,一行一行读取就行,但是会把没有数据的行读出来,需要加异常处理
            int rows = sheetAt.getPhysicalNumberOfRows();
            System.out.println(rows);
            for (int i = 0; i < rows; i++) {
                if (i == 0) {
                    continue;
                }

                //某一行的数据,是一行一行的读取
                XSSFRow row = sheetAt.getRow(i);
                if (ExcelUtils.isRowEmpty(row) || row == null) {
                    continue;
                }


                for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    cell.setCellType(CellType.STRING);
                    System.out.print(i + " " + cell.getCellType() + " ");
                }
                System.out.println();
//                System.out.println(row.getCell(6).getStringCellValue());

            }

        } catch (
                InvalidFormatException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (
                RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test03() throws ParseException {
        String s = "2020-03-05 00:00:00";
        Date date = DateUtil.dateFormat(s);
        System.out.println(date.getTime());
    }

}


