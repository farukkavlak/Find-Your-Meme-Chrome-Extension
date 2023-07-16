package com.extension.findyourmeme.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.extension.findyourmeme.generic.config.AzureBlobProperties;
import com.extension.findyourmeme.generic.enums.ErrorMessage;
import com.extension.findyourmeme.generic.exceptions.BusinessException;
import com.extension.findyourmeme.util.RandomNameGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class AzureService {
    private final AzureBlobProperties azureBlobProperties;

    private final RandomNameGenerator randomNameGenerator;
    private BlobContainerClient containerClient() {
        BlobServiceClient serviceClient = new BlobServiceClientBuilder()
                .connectionString(azureBlobProperties.getConnectionstring()).buildClient();
        BlobContainerClient container = serviceClient.getBlobContainerClient(azureBlobProperties.getContainer());
        return container;
    }
    public String storeFile(InputStream content, long length) {
        String filename = randomNameGenerator.generateRandomName();
        BlobClient client = containerClient().getBlobClient(filename);
        if (client.exists()) {
            throw new BusinessException(ErrorMessage.ITEM_IS_EXIST);
        } else {
            client.upload(content, length);
        }
        return client.getBlobUrl();
    }
}
