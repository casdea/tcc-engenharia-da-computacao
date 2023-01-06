package br.ufpa.app.android.amu.v1.dto;

import java.util.Date;

public class MapaHorarioDTO
{
	private final Date horaPrescrita;

	private Date horaAdministrada;

	public MapaHorarioDTO(Date horaPrescrita, Date horaAdministrada)
	{
		super();
		this.horaPrescrita = horaPrescrita;
		this.horaAdministrada = horaAdministrada;
	}

	public Date getHoraPrescrita()
	{
		return horaPrescrita;
	}

	public Date getHoraAdministrada()
	{
		return horaAdministrada;
	}

	public void setHoraAdministrada(Date horaAdministrada)
	{
		this.horaAdministrada = horaAdministrada;
	}
}
