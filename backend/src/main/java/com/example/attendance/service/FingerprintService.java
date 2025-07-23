package com.example.attendance.service;

import SecuGen.FDxSDKPro.jni.*;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class FingerprintService {

    private JSGFPLib sgfplib;

    public FingerprintService() {
        System.out.println("java.library.path = " + System.getProperty("java.library.path"));
        // Do not initialize hardware here!
    }

    public String captureFingerprintAsBase64(int timeout, int quality, String licstr, String templateFormat, double imageWSQRate) {
    try {
        sgfplib = new JSGFPLib();

        long initResult = sgfplib.Init(SGFDxDeviceName.SG_DEV_AUTO);
        System.out.println("Init result: " + initResult);
        if (initResult != SGFDxErrorCode.SGFDX_ERROR_NONE) {
            throw new RuntimeException("Failed to initialize scanner. Error: " + initResult);
        }

        long openResult = sgfplib.OpenDevice(0);
        System.out.println("OpenDevice result: " + openResult);
        if (openResult != SGFDxErrorCode.SGFDX_ERROR_NONE) {
            throw new RuntimeException("Failed to open scanner. Error: " + openResult);
        }

        SGDeviceInfoParam deviceInfo = new SGDeviceInfoParam();
        long infoResult = sgfplib.GetDeviceInfo(deviceInfo);
        System.out.println("GetDeviceInfo result: " + infoResult);
        if (infoResult != SGFDxErrorCode.SGFDX_ERROR_NONE) {
            throw new RuntimeException("Failed to get device info. Error: " + infoResult);
        }

        int width = deviceInfo.imageWidth;
        int height = deviceInfo.imageHeight;
        byte[] imageBuffer = new byte[width * height];

        // LED on for user guidance
        sgfplib.SetLedOn(true);

        long captureResult = sgfplib.GetImageEx(imageBuffer, timeout, quality, 0); // 0 = detect automatically
        System.out.println("GetImageEx result: " + captureResult);

        sgfplib.SetLedOn(false);

        if (captureResult != SGFDxErrorCode.SGFDX_ERROR_NONE) {
            throw new RuntimeException("Failed to capture fingerprint. Error: " + captureResult);
        }

        sgfplib.CloseDevice();

        return Base64.getEncoder().encodeToString(imageBuffer);
    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Exception while capturing fingerprint");
    }
}

    public byte[] extractTemplate(byte[] imageBuffer) {
        try {
            SGFingerInfo fingerInfo = new SGFingerInfo();
            fingerInfo.FingerNumber = 1;
            fingerInfo.ImageQuality = 100;
            fingerInfo.ImpressionType = 0;
            fingerInfo.ViewNumber = 1;

            int[] maxTemplateSize = new int[1];
            long sizeResult = sgfplib.GetMaxTemplateSize(maxTemplateSize);
            if (sizeResult != 0) {
                throw new RuntimeException("Failed to get max template size. Error: " + sizeResult);
            }

            byte[] template = new byte[maxTemplateSize[0]];

            long result = sgfplib.CreateTemplate(fingerInfo, imageBuffer, template);
            if (result != 0) {
                throw new RuntimeException("Failed to create fingerprint template. Error: " + result);
            }

            return template;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while extracting fingerprint template");
        }
    }

    public boolean matchTemplates(byte[] template1, byte[] template2) {
        try {
            boolean[] matched = new boolean[1];
            long result = sgfplib.MatchTemplate(template1, template2, 5, matched); // 5 = FAR level (lower = more secure)

            if (result != 0) {
                throw new RuntimeException("Template matching failed. Error: " + result);
            }

            return matched[0];
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while matching templates");
        }
    }
}
