import React, { useState } from 'react';
import Modal from '../Modal/Modal';
import './evento-card.component.css';

export interface EventoCardProps {
  evento: {
    nombre: string;
    escudo1Url?: string;
    escudo2Url?: string;
    equipo1: { nombre: string };
    equipo2: { nombre: string };
  };
}

const EventoCard: React.FC<EventoCardProps> = ({ evento }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleCardClick = () => {
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };

  return (
    <>
      <div className="evento-card" onClick={handleCardClick}>
        <div className="evento-nombre">{evento.nombre}</div>
        <div className="evento-escudos">
          {evento.escudo1Url && (
            <img src={evento.escudo1Url} alt={`${evento.equipo1.nombre} escudo`} className="evento-escudo" />
          )}
          <span className="evento-vs">vs</span>
          {evento.escudo2Url && (
            <img src={evento.escudo2Url} alt={`${evento.equipo2.nombre} escudo`} className="evento-escudo" />
          )}
        </div>
      </div>

      <Modal isOpen={isModalOpen} onClose={handleCloseModal}>
        <div className="evento-card-modal">
    <div className="evento-nombre">{evento.nombre}</div>
    <div className="evento-escudos">
      {evento.escudo1Url && (
        <img src={evento.escudo1Url} alt={`${evento.equipo1.nombre} escudo`} className="evento-escudo" />
      )}
      <span className="evento-vs">vs</span>
      {evento.escudo2Url && (
        <img src={evento.escudo2Url} alt={`${evento.equipo2.nombre} escudo`} className="evento-escudo" />
      )}
    </div>
  </div>
      </Modal>
    </>
);
};

export default EventoCard; 