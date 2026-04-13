import { useState, useEffect, useCallback } from 'react';

const BASE = '/clientes';

async function apiFetch(path, options = {}) {
  const res = await fetch(`${BASE}${path}`, {
    headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
    ...options,
  });
  if (!res.ok) {
    const body = await res.text().catch(() => '');
    const err = new Error(body || `HTTP ${res.status}`);
    err.status = res.status;
    throw err;
  }
  // 204 No Content
  if (res.status === 204) return null;
  return res.json();
}

// ── Hook principal ────────────────────────────────────────────────────────────
export function useClientes() {
  const [clientes, setClientes] = useState([]);
  const [loading, setLoading]   = useState(true);
  const [error, setError]       = useState(null);
  const [online, setOnline]     = useState(true);

  const carregar = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await apiFetch('');
      setClientes(data);
      setOnline(true);
    } catch (e) {
      setError(e.message);
      setOnline(false);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => { carregar(); }, [carregar]);

  const cadastrar = useCallback(async (dto) => {
    const novo = await apiFetch('', { method: 'POST', body: JSON.stringify(dto) });
    setClientes((prev) => [...prev, novo]);
    return novo;
  }, []);

  const atualizar = useCallback(async (id, dto) => {
    const atualizado = await apiFetch(`/${id}`, { method: 'PUT', body: JSON.stringify(dto) });
    setClientes((prev) => prev.map((c) => (c.id === id ? atualizado : c)));
    return atualizado;
  }, []);

  const excluir = useCallback(async (id) => {
    await apiFetch(`/${id}`, { method: 'DELETE' });
    setClientes((prev) => prev.filter((c) => c.id !== id));
  }, []);

  const buscarPorId = useCallback(async (id) => {
    return apiFetch(`/${id}`);
  }, []);

  return { clientes, loading, error, online, carregar, cadastrar, atualizar, excluir, buscarPorId };
}
