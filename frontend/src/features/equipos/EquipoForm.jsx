import { useState, useEffect } from 'react';
import api from '../../services/api';

const EquipoForm = ({ equipoEditar, onGuardadoExitoso, onCancelar }) => {
    const [equipo, setEquipo] = useState({
        tipoEquipo: '',
        marca: '',
        numeroSerie: '',
        estado: 'Activo'
    });
    const [error, setError] = useState(null);

    useEffect(() => {
        if (equipoEditar) {
            setEquipo(equipoEditar);
        } else {
            setEquipo({ tipoEquipo: '', marca: '', numeroSerie: '', estado: 'Activo' });
        }
    }, [equipoEditar]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEquipo(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        try {
            if (equipo.id) {
                await api.put(`/equipos/${equipo.id}`, equipo);
            } else {
                await api.post('/equipos', equipo);
            }
            onGuardadoExitoso();
            setEquipo({ tipoEquipo: '', marca: '', numeroSerie: '', estado: 'Activo' });
        } catch (err) {
            setError(err.response?.data || 'Error al guardar el equipo');
        }
    };

    return (
        <div className="bg-white p-6 rounded-lg shadow-md mb-8">
            <h2 className="text-xl font-semibold mb-4 text-gray-800">
                {equipo.id ? 'Editar Equipo' : 'Registrar Nuevo Equipo'}
            </h2>
            {error && <div className="bg-red-100 text-red-700 p-3 rounded mb-4">{error}</div>}
            
            <form onSubmit={handleSubmit} className="space-y-4">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                        <label className="block text-sm font-medium text-gray-700">Tipo de Equipo</label>
                        <input
                            type="text"
                            name="tipoEquipo"
                            value={equipo.tipoEquipo}
                            onChange={handleChange}
                            required
                            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 p-2 border"
                            placeholder="Ej. Laptop, Monitor"
                        />
                    </div>
                    <div>
                        <label className="block text-sm font-medium text-gray-700">Marca</label>
                        <input
                            type="text"
                            name="marca"
                            value={equipo.marca}
                            onChange={handleChange}
                            required
                            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 p-2 border"
                        />
                    </div>
                    <div>
                        <label className="block text-sm font-medium text-gray-700">Número de Serie</label>
                        <input
                            type="text"
                            name="numeroSerie"
                            value={equipo.numeroSerie}
                            onChange={handleChange}
                            required
                            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 p-2 border"
                        />
                    </div>
                    <div>
                        <label className="block text-sm font-medium text-gray-700">Estado</label>
                        <select
                            name="estado"
                            value={equipo.estado}
                            onChange={handleChange}
                            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 p-2 border bg-white"
                        >
                            <option value="Activo">Activo</option>
                            <option value="En Mantenimiento">En Mantenimiento</option>
                            <option value="Baja">Baja</option>
                        </select>
                    </div>
                </div>
                
                <div className="flex justify-end space-x-3 pt-4">
                    {equipo.id && (
                        <button
                            type="button"
                            onClick={onCancelar}
                            className="px-4 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
                        >
                            Cancelar
                        </button>
                    )}
                    <button
                        type="submit"
                        className="px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
                    >
                        {equipo.id ? 'Actualizar' : 'Guardar'}
                    </button>
                </div>
            </form>
        </div>
    );
};

export default EquipoForm;
