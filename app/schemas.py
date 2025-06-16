from typing import Optional
from sqlmodel import SQLModel

class SuiviClientSchema(SQLModel):
    id: int
    nom: Optional[str] = None
    email: str
    nomLength: int
