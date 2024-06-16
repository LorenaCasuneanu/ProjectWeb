import { Component } from '@angular/core';
import {ImageService} from "./service/imageService";
import {Image} from "./entities/image";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'BMP Picture';
  key: string = '';
  selectedImage: string | ArrayBuffer | null = null;


  constructor(private service: ImageService) { }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = e => this.selectedImage = reader.result;
      reader.readAsDataURL(file);
    }
  }

  check(){
    if (!this.key || !this.selectedImage) {
      alert('Please enter a key and select an image.');
      return;
    }
  }

  encrypt() {
    this.check()
    let image = new Image();
    image.key = this.key;
    image.image = this.selectedImage;
    image.encrypt = true;

    this.service.encryptImage(image).subscribe();
  }

  decrypt() {
    this.check();
    let image = new Image();
    image.key = this.key;
    image.image = this.selectedImage;
    image.encrypt = false;

    this.service.encryptImage(image).subscribe();
  }


}
