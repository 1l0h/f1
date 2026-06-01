document.addEventListener('DOMContentLoaded', ()=>{
  const cards = document.querySelectorAll('.circuito');
  const overlay = document.createElement('div');
  overlay.className = 'overlay';
  document.body.appendChild(overlay);

  // Dynamic horizontal gap calculation: keeps card size fixed (260px)
  // and increases lateral gap up to maxGap when extra space is available.
  const circuitContainer = document.getElementById('Circuitos');
  const CARD_W = 260; // must match CSS
  const MIN_GAP = 16; // 1rem approx
  const MAX_GAP = 128; // 3rem approx

  function updateColGap(){
    if (!circuitContainer) return;
    const style = getComputedStyle(circuitContainer);
    const paddingLeft = parseFloat(style.paddingLeft) || 0;
    const paddingRight = parseFloat(style.paddingRight) || 0;
    const containerWidth = circuitContainer.clientWidth - paddingLeft - paddingRight;
    // how many columns could fit at minimum gap
    const colsFit = Math.floor((containerWidth + MIN_GAP) / (CARD_W + MIN_GAP));
    const cols = Math.max(1, colsFit);
    const leftover = containerWidth - (cols * CARD_W);
    const gap = Math.max(MIN_GAP, Math.min(MAX_GAP, leftover / (cols + 1)));
    circuitContainer.style.setProperty('--col-gap', gap + 'px');
  }
  // run on load and resize
  window.addEventListener('resize', updateColGap);
  // small delay after fonts/images load to get correct sizes
  window.addEventListener('load', ()=>{ updateColGap(); });
  updateColGap();

  document.querySelectorAll('#Carrusel .tarjeta').forEach(logo => {
    logo.addEventListener('click', ()=>{
      // intentar usar el alt del <img> dentro de la tarjeta como parámetro de escudería
      const img = logo.querySelector('img');
      const team = img ? img.alt || img.getAttribute('data-name') || '' : '';
      const url = team ? `Equipo/index.html?e=${encodeURIComponent(team)}` : 'Equipo/index.html';
      window.location.href = url;
    });
  });

  function openCard(card){
    // quitar active de otras
    document.querySelectorAll('.circuito.active').forEach(c=>c.classList.remove('active'));
    card.classList.add('active');
    overlay.classList.add('visible');
    // marcar body para ocultar las demás tarjetas
    document.body.classList.add('card-open');
    // prevenir scroll de fondo opcional
    document.documentElement.style.overflow = 'hidden';
    // cambiar imagen a GIF
    const img = card.querySelector('img');
    if (img) {
      const circuitName = card.getAttribute('data-circuit');
      const gifPath = img.src.replace(/0001\.webp$/, `${circuitName}.gif`);
      img.src = gifPath;
    }
  }
  function closeAll(){
    document.querySelectorAll('.circuito.active').forEach(c=>{
      c.classList.remove('active');
      // restaurar imagen original
      const img = c.querySelector('img');
      if (img) {
        img.src = img.src.replace(/[^\/]+\.gif$/, '0001.webp');
      }
    });
    overlay.classList.remove('visible');
    // quitar la marca del body
    document.body.classList.remove('card-open');
    document.documentElement.style.overflow = '';
  }

  cards.forEach(card=>{
    card.addEventListener('click', (e)=>{
      // si se clicó el botón cerrar, no navegar ni reabrir
      if (e.target.closest('.close')) return;
      if (!card.classList.contains('active')) {
        openCard(card);
      } else {
        // tarjeta ya activa: navegar a la página del circuito
        const circuitName = card.getAttribute('data-circuit');
        if (circuitName) {
          const url = `Circuito/index.html?c=${encodeURIComponent(circuitName)}`;
          window.location.href = url;
        }
      }
    });
    const closeBtn = card.querySelector('.close');
    if (closeBtn) closeBtn.addEventListener('click', (e)=>{ e.stopPropagation(); closeAll(); });
  });

  overlay.addEventListener('click', closeAll);
  document.addEventListener('keydown', (e)=>{ if (e.key === 'Escape') closeAll(); });
});