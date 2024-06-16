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

int DecryptImage(unsigned char* ciphertextImage, int ciphertextImageLen, unsigned char* key, unsigned char* plaintext)
{

    EVP_CIPHER_CTX* ctx;
    int len = 0;

    int plaintext_len = 0;
    ctx = EVP_CIPHER_CTX_new();
    /* Initialise key and IV */
    EVP_DecryptInit_ex(ctx, EVP_aes_256_ecb(), NULL, key, NULL);
    EVP_DecryptUpdate(ctx, plaintext, &len, ciphertextImage, ciphertextImageLen);

    //  ERR_print_errors_fp(stderr);

    plaintext_len = len;

    int a = EVP_DecryptFinal_ex(ctx, plaintext + len, &len);
    printf("%d\n", a);
    plaintext_len += len;
    //ERR_print_errors_fp(stderr);
    /* Clean up */
    EVP_CIPHER_CTX_free(ctx);

    return plaintext_len;
}

int main(int argc, char* argv[]) {

    int rank, size;
    unsigned char Output[1024];
    unsigned char Output_Dec[1024];
    int Output_len = 0;
    int output_len = 0;

    MPI_Init(&argc, &argv); // Initialize MPI environment

    MPI_Comm_rank(MPI_COMM_WORLD, &rank); // Get rank of the process
    MPI_Comm_size(MPI_COMM_WORLD, &size); // Get total number of processes
    //len base64 key
    EVP_DecodeBlock(Output, (unsigned char*)argv[2], atoi(argv[1]));
    output_len = DecryptImage(Output, Output_len, (unsigned char*)argv[3], Output_Dec);
    EVP_EncodeBlock(Output_Dec, Output, output_len);
    ofstream MyFile("output.bin", ios::binary);
    MyFile.write((char*)Output_Dec, output_len);

    printf("Greetings from process %i\n", rank);

    MPI_Finalize(); // Finalize MPI environment
    return 0;
}


