import { useState, useEffect } from 'react';
import api from './services/api';
import EquipoList from './features/equipos/EquipoList';
import EquipoForm from './features/equipos/EquipoForm';

// Este es el componente principal. Aquí controlamos los estados globales de la app.
function App() {
    const [equipos, setEquipos] = useState([]);
    const [equipoEditando, setEquipoEditando] = useState(null); // Guarda el objeto equipo que estamos editando en el formulario
    const [busqueda, setBusqueda] = useState(''); // Guarda el texto escrito en la barra de búsqueda

    // Cargar la lista completa de equipos desde nuestra API Spring Boot
    const cargarEquipos = async () => {
        try {
            const response = await api.get('/equipos');
            setEquipos(response.data);
        } catch (error) {
            console.error("Error al cargar equipos:", error);
        }
    };

    // useEffect corre al iniciar la app. Hace que se carguen los equipos apenas abre la página.
    useEffect(() => {
        cargarEquipos();
    }, []);

    // Se dispara al presionar "Editar" en la lista. Carga el equipo en el form y hace scroll arriba.
    const handleEditar = (equipo) => {
        setEquipoEditando(equipo);
        window.scrollTo({ top: 0, behavior: 'smooth' });
    };

    // Eliminar equipo por ID
    const handleEliminar = async (id) => {
        if (window.confirm('¿Estás seguro de eliminar este equipo?')) {
            try {
                await api.delete(`/equipos/${id}`);
                cargarEquipos(); // Recargar la lista después de borrar
                // Si el equipo que estábamos editando justo fue eliminado, reseteamos el formulario
                if (equipoEditando && equipoEditando.id === id) {
                    setEquipoEditando(null);
                }
            } catch (error) {
                console.error("Error al eliminar:", error);
                alert("No se pudo eliminar el equipo");
            }
        }
    };

    // Cancelar la edición y limpiar el formulario
    const handleCancelEdit = () => {
        setEquipoEditando(null);
    };

    return (
        <div className="min-h-screen bg-gray-100 py-8 px-4 sm:px-6 lg:px-8">
            <div className="max-w-4xl mx-auto">
                <header className="mb-8 text-center">
                    <h1 className="text-3xl font-bold text-gray-900">
                        Sistema de Gestión de Activos TI
                    </h1>
                    <p className="mt-2 text-sm text-gray-600">
                        Inventario y Control de Equipos
                    </p>
                </header>

                <main>
                    {/* Componente del Formulario */}
                    <EquipoForm
                        equipoEditar={equipoEditando}
                        onGuardadoExitoso={() => {
                            cargarEquipos();
                            setEquipoEditando(null);
                        }}
                        onCancelar={handleCancelEdit}
                    />

                    <div className="mt-8">
                        {/* Buscador y Título de la tabla */}
                        <div className="flex flex-col sm:flex-row justify-between items-center mb-4 gap-4">
                            <h2 className="text-xl font-semibold text-gray-800">Lista de Equipos</h2>
                            <div className="relative w-full sm:w-64">
                                <input
                                    type="text"
                                    placeholder="Buscar por marca o serie..."
                                    value={busqueda}
                                    onChange={(e) => setBusqueda(e.target.value)}
                                    className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-md focus:ring-blue-500 focus:border-blue-500 shadow-sm"
                                />
                                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                    <svg className="h-5 w-5 text-gray-400" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                                        <path fillRule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clipRule="evenodd" />
                                    </svg>
                                </div>
                            </div>
                        </div>

                        {/* Tabla de Equipos : Filtramos la lista en tiempo real usando .filter() de JS */}
                        <EquipoList
                            equipos={equipos.filter(eq =>
                                eq.marca.toLowerCase().includes(busqueda.toLowerCase()) ||
                                eq.numeroSerie.toLowerCase().includes(busqueda.toLowerCase()) ||
                                eq.tipoEquipo.toLowerCase().includes(busqueda.toLowerCase())
                            )}
                            onEditar={handleEditar}
                            onEliminar={handleEliminar}
                        />
                    </div>
                </main>
            </div>
        </div>
    );
}

export default App;

