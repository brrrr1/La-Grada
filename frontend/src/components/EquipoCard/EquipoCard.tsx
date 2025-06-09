import React, { useState } from 'react';
import Modal from '../Modal/Modal';
import './equipo-card.component.css';

export interface EquipoCardProps {
  equipo: {
    nombre: string;
    escudoUrl?: string;
    fondoUrl?: string;
  };
}

const EquipoCard: React.FC<EquipoCardProps> = ({ equipo }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleCardClick = () => {
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };

  return (
    <>
      <div className="equipo-card" onClick={handleCardClick}>
        <div className="card-fondo">
          {equipo.fondoUrl && (
            <img src={equipo.fondoUrl} alt="Fondo estadio" className="fondo-blur" />
          )}
        </div>
        <div className="card-nombre-escudo">
          <div className="card-nombre">
            <span className="equipo-nombre">{equipo.nombre}</span>
          </div>
          {equipo.escudoUrl && (
            <img className="equipo-escudo" src={equipo.escudoUrl} alt={`${equipo.nombre} escudo`} />
          )}
        </div>
      </div>

      <Modal isOpen={isModalOpen} onClose={handleCloseModal}>
        <div className="equipo-card-modal">
    <div className="card-fondo">
      {equipo.fondoUrl && (
        <img src={equipo.fondoUrl} alt="Fondo estadio" className="fondo-blur" />
      )}
    </div>
    <div className="card-nombre-escudo">
      <div className="card-nombre">
        <span className="equipo-nombre">{equipo.nombre}</span>
      </div>
      {equipo.escudoUrl && (
        <img className="equipo-escudo" src={equipo.escudoUrl} alt={`${equipo.nombre} escudo`} />
      )}
    </div>
  </div>
      </Modal>
    </>
);
};

export default EquipoCard; 