package com.portfolio.deepak.service;

import com.portfolio.deepak.entity.Certificate;
import com.portfolio.deepak.repository.CertificateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateService {

    private final CertificateRepository certificateRepository;

    public CertificateService(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    public List<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }

    public Certificate getCertificateById(Long id) {
        return certificateRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Certificate not found"));
    }

    public Certificate saveCertificate(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    public void deleteCertificate(Long id) {
        certificateRepository.deleteById(id);
    }
}