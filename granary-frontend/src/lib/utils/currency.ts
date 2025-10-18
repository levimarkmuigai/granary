export function formatKES(cents: number): string {
  return new Intl.NumberFormat("en-KE", {
    style: "currency",
    currency: "KES",
    minimumFractionDigits: 0,
    maximumFractionDigits: 0,
  }).format(cents / 100);
}

export function formatKESCompact(cents: number): string {
  const kes = cents / 100;
  if (kes >= 1000) {
    return `KES ${(kes / 1000).toFixed(1)}K`;
  }
  return formatKES(cents);
}

export function kesToCents(kes: number): number {
  return Math.round(kes * 100);
}


export function centsToKES(cents: number): number {
  return cents / 100;
}

export function formatPrice(cents: number): string {
  return new Intl.NumberFormat("en-KE", {
    minimumFractionDigits: 0,
    maximumFractionDigits: 0,
  }).format(cents / 100);
}

export function isValidPrice(cents: number): boolean {
  return cents > 0 && cents < 100000000; 
}