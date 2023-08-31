package kz.tele2.crmoda.report;

import kz.tele2.crmoda.model.onec.Counterparty;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportGenerator {
    public byte[] generateReport(Counterparty counterparty, String site, String sum, LocalDate start, Boolean signed) {
        try {
            URL jrxmlUrl = getClass().getResource("/report/completionCertificate.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlUrl.getPath());

            // Prepare data source (replace with your own data)
            List<Map<String, Object>> dataSource = new ArrayList<>();
            Map<String, Object> data = new HashMap<>();
            data.put("owner", "Товарищество с ограниченной ответственностью \"Мобайл Телеком Сервис\", 050000 РК, г.Алматы ул. Желтоксан, 115, тел.: 87273");
            data.put("executor", counterparty.getName());
            data.put("contract", counterparty.getCode());
            data.put("docnum", "1");
            data.put("docdate", start.toString());
            data.put("executorbin", counterparty.getIdentification_code());
            data.put("ownerbin", "041140004799");
            data.put("workdate", start.withDayOfMonth(1).toString());
            data.put("worktitle", "Аренда БТС");
            data.put("ordernum", "1");
            data.put("count", "1");
            data.put("price", sum);
            data.put("sum", sum);
            data.put("totalsum", sum);
            data.put("signdate", LocalDate.now().toString());
            data.put("addinfo", "");
            // Add more fields as needed
            dataSource.add(data);

            // Convert data source to JRDataSource
            JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrDataSource);

            JRPdfExporter pdfExporter = new JRPdfExporter();
            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
            pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfOutputStream));
            pdfExporter.exportReport();
            System.out.println("Report generated successfully.");
            return pdfOutputStream.toByteArray();

        } catch (JRException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
