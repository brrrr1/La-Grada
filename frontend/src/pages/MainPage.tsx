import React, { useEffect, useState } from 'react';
import EquipoCard from '../components/EquipoCard/EquipoCard';
import EventoCard from '../components/EventoCard/EventoCard';
import './main/main.component.css';

interface Equipo {
  nombre: string;
  escudoUrl?: string;
  fondoUrl?: string;
}

interface Evento {
  nombre: string;
  escudo1Url?: string;
  escudo2Url?: string;
  equipo1: { nombre: string };
  equipo2: { nombre: string };
}

const MainPage: React.FC = () => {
  const [equipos, setEquipos] = useState<Equipo[]>([]);
  const [eventos, setEventos] = useState<Evento[]>([]);
  const [loading, setLoading] = useState(true);
  const [loadingEventos, setLoadingEventos] = useState(true);
  const [errorMsg, setErrorMsg] = useState('');
  const [errorEventos, setErrorEventos] = useState('');

  useEffect(() => {
    setTimeout(() => {
      setEquipos([
        { nombre: 'Equipo 1', escudoUrl: '', fondoUrl: '' },
        { nombre: 'Equipo 2', escudoUrl: '', fondoUrl: '' },
      ]);
      setLoading(false);
    }, 1000);
    setTimeout(() => {
      setEventos([
        { nombre: 'Evento 1', escudo1Url: '', escudo2Url: '', equipo1: { nombre: 'Equipo 1' }, equipo2: { nombre: 'Equipo 2' } },
      ]);
      setLoadingEventos(false);
    }, 1200);
  }, []);

  return (
    <div className="main-container">
      <h2 className="equipos-title">
        <a href="/equipos" style={{ color: 'inherit', textDecoration: 'none', cursor: 'pointer' }}>Equipos</a>
      </h2>
      {loading && <div>Cargando equipos...</div>}
      {errorMsg && <div className="error">{errorMsg}</div>}
      <div className="equipos-grid">
        {equipos.map((equipo, idx) => (
          <EquipoCard key={idx} equipo={equipo} />
        ))}
      </div>
      <h2 className="eventos-title">Eventos</h2>
      {loadingEventos && <div>Cargando eventos...</div>}
      {errorEventos && <div className="error">{errorEventos}</div>}
      <div className="eventos-grid">
        {eventos.map((evento, idx) => (
          <EventoCard key={idx} evento={evento} />
        ))}
      </div>
    </div>
  );
};

export default MainPage; 