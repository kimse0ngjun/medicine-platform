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
