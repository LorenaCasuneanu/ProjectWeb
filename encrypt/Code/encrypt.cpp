// MyProject.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

//#pragma warning(disable : 4996)
#include <stdio.h>
#include <mpi.h>
#include <string>
#include <openssl/evp.h>
#include <iostream>
#include <fstream>
#include <openssl/err.h>

using namespace std;

int EncryptImage(unsigned char* Image, int ImageLen, unsigned char* key, unsigned char* ciphertext)
{
  
    EVP_CIPHER_CTX* ctx;
    int len = 0;

    int ciphertext_len = 0;
    ctx = EVP_CIPHER_CTX_new();
          /* Initialise key and IV */
    EVP_EncryptInit_ex2(ctx, EVP_aes_256_ecb(), key, NULL ,NULL);

    EVP_EncryptUpdate(ctx, ciphertext, &len, Image, ImageLen);

    ciphertext_len = len;

    EVP_EncryptFinal_ex(ctx, ciphertext + len, &len);

    ciphertext_len += len;

    /* Clean up */
    EVP_CIPHER_CTX_free(ctx);

    return ciphertext_len;
}

int main(int argc, char* argv[]) {
	
	int rank, size;
    unsigned char Output[1024];
    unsigned char Output_Dec[1024];
    int Output_len = 0;
    int bla_len = 0;

	MPI_Init(&argc, &argv); // Initialize MPI environment

	MPI_Comm_rank(MPI_COMM_WORLD, &rank); // Get rank of the process
	MPI_Comm_size(MPI_COMM_WORLD, &size); // Get total number of processes
    // len base64 key
    EVP_DecodeBlock(Output, (unsigned char*)argv[2], atoi(argv[1]));
    Output_len = EncryptImage((unsigned char*) argv[1], 6, (unsigned char*) argv[3], Output);
    EVP_EncodeBlock(Output_Dec, Output, bla_len);

    ofstream MyFile("filename_output.txt", ios::binary);
    MyFile.write((char*)Output_Dec, bla_len);
	MPI_Finalize(); // Finalize MPI environment
	return 0;
}