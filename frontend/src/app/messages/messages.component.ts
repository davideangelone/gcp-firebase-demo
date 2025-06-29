import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { finalize } from 'rxjs/operators';
import { environment } from '../../environments/environment';

interface Message {
  id?: string;  // id nullo in fase di invio
  timestamp?: string; // timestamp nullo in fase di invio
  text: string;
}

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.css'],
  imports: [FormsModule, HttpClientModule, CommonModule],
  standalone: true
})
export class MessagesComponent implements OnInit {
  message : string = '';
  messages : Message[] = [];
  apiUrl = `${environment.apiUrl}/api/messages`;
  isSending: boolean = false;
  isDeleting: boolean = false;

  constructor(private http: HttpClient) {
    console.log('Api URL:', this.apiUrl);
  }

  sendMessage() {
    if (!this.message || this.message.trim() === '') return;

    this.isSending = true;
    const newMessage: Message = { text: this.message };
    this.http.post<Message>(this.apiUrl, newMessage)
      .pipe(
        finalize(() => this.isSending = false)
      )
      .subscribe({
        next: (data) => {
          this.messages.push(data);
          this.message = '';
        },
        error: () => {
          // Gestione errore (opzionale)
          console.error('Errore di invio del messaggio');
        }
      });
  }

  fetchMessages() {
    this.http.get<Message[]>(this.apiUrl)
      .subscribe(data => this.messages = data);
  }

  deleteMessage(id: string) {
    if (!id || id.trim() === '') return;

    this.isDeleting = true;
    this.http.delete(`${this.apiUrl}/${id}`)
      .pipe(
        finalize(() => this.isDeleting = false)
      )
      .subscribe({
        next: () => {
          this.messages = this.messages.filter(msg => msg.id !== id);
        },
        error: () => {
          // Gestione errore (opzionale)
          console.error('Errore di cancellazione del messaggio');
        }
      });
  }

  ngOnInit() {
    this.fetchMessages();
  }
}
