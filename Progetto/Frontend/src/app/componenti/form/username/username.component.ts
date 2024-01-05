import { Component, Input } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-username',
  templateUrl: './username.component.html',
  styleUrl: './username.component.css'
})
export class UsernameComponent {

  @Input() username: string;
  @Input() label: string;
  @Input() type: string;
  @Input() control: FormControl;

}
