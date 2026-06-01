// Helpers para consumir la API del backend
// Usar la IP/puerto proporcionados para llamadas desde el navegador
const PRIMARY_API_BASE = 'http://100.56.24.223:8080/f1';
const RELATIVE_API_BASE = '/f1';

async function tryFetch(url, opts) {
  try {
    const res = await fetch(url, opts);
    return res;
  } catch (e) {
    // network error - return null to allow fallback
    return null;
  }
}

function qParam(name, defaultValue) {
  const params = new URLSearchParams(window.location.search);
  return params.get(name) || defaultValue;
}

async function fetchJSON(path, opts) {
  // path may be absolute or relative; if it's relative, try PRIMARY then RELATIVE
  const tryUrls = [];
  if (path.startsWith('http://') || path.startsWith('https://')) {
    tryUrls.push(path);
  } else {
    tryUrls.push(PRIMARY_API_BASE + path);
    tryUrls.push(RELATIVE_API_BASE + path);
  }

  let lastErr = null;
  for (const url of tryUrls) {
    const res = await tryFetch(url, opts);
    if (!res) {
      lastErr = new Error('Network error');
      continue;
    }
    if (!res.ok) {
      lastErr = new Error(`HTTP ${res.status}`);
      lastErr.status = res.status;
      // for 404 try next option, otherwise throw
      if (res.status >= 500) throw lastErr;
      // try next
      continue;
    }
    return res.json();
  }
  throw lastErr || new Error('Unknown fetch error');
}

async function fetchPilotoByNumero(numero) {
  return fetchJSON(`/pilotos/${numero}`);
}

async function fetchEscuderiaByNombre(nombre) {
  // try exact endpoint first
  try {
    return await fetchJSON(`/escuderias/nombre/${encodeURIComponent(nombre)}`);
  } catch (e) {
    if (e.status === 404) {
      // fallback: fetch all and match by substring
      const all = await fetchJSON(`/escuderias`);
      const found = all.find(x => x.nombre && x.nombre.toLowerCase().includes(nombre.toLowerCase()));
      if (found) return found;
      throw e;
    }
    throw e;
  }
}

async function fetchGranPremioByName(name) {
  try {
    return await fetchJSON(`/granpremios/nombre/${encodeURIComponent(name)}`);
  } catch (e) {
    if (e.status === 404) {
      const all = await fetchJSON(`/granpremios`);
      const found = all.find(gp => (gp.nombre && gp.nombre.toLowerCase().includes(name.toLowerCase())) || (gp.ubicacion && gp.ubicacion.toLowerCase().includes(name.toLowerCase())));
      if (found) return found;
      throw e;
    }
    throw e;
  }
}
