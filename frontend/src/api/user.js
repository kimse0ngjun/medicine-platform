export const getMyUser = async (token) => {
  const res = await fetch(
    `${import.meta.env.VITE_API_BASE_URL}/api/v1/users/me`,
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    },
  );

  if (!res.ok) {
    throw new Error(`user API 실패: ${res.status}`);
  }

  return res.json();
};

export const getMyVerifications = async () => {
  const token = localStorage.getItem("token");

  const res = await fetch("/api/v1/verifications", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify({
      inputText: keyword,
      lotNumber: lot,
      result: result,
    }),
  });

  if (!res.ok) throw new Error("verification 실패");

  return res.json();
};
