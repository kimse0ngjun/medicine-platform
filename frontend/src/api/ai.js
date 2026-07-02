const BASE_URL = "http://localhost:8080";

export async function generateAiSummary(productName) {
  const res = await fetch(
    `${BASE_URL}/api/v1/ai/summary?productName=${encodeURIComponent(productName)}`,
    { method: "POST" },
  );

  if (!res.ok) {
    const text = await res.text();
    throw new Error(text);
  }

  return await res.json();
}
